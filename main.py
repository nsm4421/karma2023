import win32com.client as win32
from PyPDF2 import PdfMerger
import pandas as pd
from tqdm import tqdm
import argparse
import os
import sys


class Util:

    @staticmethod
    def open_hwp_doc(hwp_file_path: str) -> any:
        """
        한글 파일을 열고, Callback함수를 실행
        - hwp_file_path : 한글 파일 경로를 적어줌 (함수 내부에서 절대경로로 변경)
        """
        hwp = win32.gencache.EnsureDispatch("HWPFrame.HwpObject")
        # 보안모듈 등록 - 레지스트리 편집기에 등록한 이름(AutomationModule)을 인자로 넘겨주기!
        hwp.RegisterModule('FilePathCheckDLL', 'AutomationModule')
        # visible = False → 백그라운드에서 실행
        hwp.XHwpWindows.Item(0).Visible = False
        # 한글파일 열기
        hwp.Open(os.path.abspath(hwp_file_path))
        return hwp

    @staticmethod
    def save_hwp_doc(hwp, path) -> None:
        """
        한글 파일을 저장하는 메써드
        """
        hwp.SaveAs(os.path.abspath(path))

    @staticmethod
    def close_hwp_doc(hwp) -> None:
        """
        한글 파일을 닫는 메써드
        """
        hwp.XHwpDocuments.Close(isDirty=False)
        hwp.Quit()

    @staticmethod
    def change_text_color(hwp: any) -> None:
        # 빨간글씨 → 검은글씨
        option = hwp.HParameterSet.HFindReplace
        hwp.HAction.GetDefault("AllReplace", option.HSet)
        option.FindString = ""
        option.ReplaceString = ""
        option.IgnoreMessage = 1
        option.FindCharShape.TextColor = hwp.RGBColor(255, 0, 0)
        option.ReplaceCharShape.TextColor = hwp.RGBColor(0, 0, 0)
        hwp.HAction.Execute("AllReplace", option.HSet)
        # 파른글씨 → 검은글씨
        option = hwp.HParameterSet.HFindReplace
        hwp.HAction.GetDefault("AllReplace", option.HSet)
        option.FindString = ""
        option.ReplaceString = ""
        option.IgnoreMessage = 1
        option.FindCharShape.TextColor = hwp.RGBColor(0, 0, 255)
        option.ReplaceCharShape.TextColor = hwp.RGBColor(0, 0, 0)
        hwp.HAction.Execute("AllReplace", option.HSet)

    @staticmethod
    def convert_hwp_to_pdf(hwp, pdf_file_path, printmethod=0) -> None:
        """
        한글 파일을 두쪽모음 해제 후 PDF 파일로 변환해주는 메써드
        - hwp : 저장할 hwp 문서
        - pdf_file_path : 저장할 pdf 문서 경로
        - pringtmode : 0이면 한 페이지 당 한면(변경하면 두쪽모음, 네쪽모음 해제 가능)
        """
        action = hwp.CreateAction("Print")
        print_setting = action.CreateSet()
        action.GetDefault(print_setting)
        print_setting.SetItem("PrintMethod", printmethod)
        print_setting.SetItem("FileName", os.path.abspath(pdf_file_path))
        print_setting.SetItem("PrinterName", "Microsoft Print to PDF")
        action.Execute(print_setting)

    @staticmethod
    def merge_pdf(pdf_files: list, save_file_path: str) -> None:
        """
        여러 pdf파일을 하나의 pdf 파일로 저장해주는 메써드
        """
        pdf_merger = PdfMerger(strict=False)
        for file in pdf_files:
            pdf_merger.append(file)
        pdf_merger.write(save_file_path)
        pdf_merger.close()


class Rpa(Util):
    def __init__(cls, hwp_doc_dir, list_file_path, sheet_name="list", output_dir="./ouput", working_dir="./working", prefix="001_", suffix="_공시용"):
        """
        hwp_doc_dir : 사업방법서 파일 경로
        list_file_path : 주계약↔독립특약 Mapping을 작성한 Excel 파일경로
        sheet_name : Excel파일에서 작성 시트명
        output_dir : 결과물을 저장할 폴더 경로       
        working_folder : 작업폴더명
        prefix : 사업방법서 hwp 파일명 prefix
        suffix : 병합된 pdf 파일명 suffix
        """
        cls.hwp_doc_dir = os.path.abspath(hwp_doc_dir)
        cls.list_file_path = os.path.abspath(list_file_path)
        cls.sheet_name = sheet_name
        cls.output_dir = os.path.abspath(output_dir)
        cls.working_dir = os.path.abspath(working_dir)
        cls.prefix = prefix
        cls.suffix = suffix
        # key : 주계약 단위상품이력코드(str) / value : 독립특약 단위상품이력코드들(list)
        cls.prod_mapping = {}
        # key : 계약 단위상품이력코드(str) / value : 파일명(str)
        cls.filename_mapping = {}

    def main(cls):
        cls.check_folder_exist()
        cls.set_ctr_mapping()
        cls.set_filename_mapping()
        cls.modify_hwp_docs()
        cls.merge_pdf_files_by_mapping()

    def check_folder_exist(cls):
        """
        Input을 체크하고, 작업폴더, 결과폴더가 없으면 새로 만들기
        """
        if not os.path.exists(cls.list_file_path):
            raise Exception("엑셀 파일 없음")
        if not os.path.exists(cls.hwp_doc_dir):
            raise Exception("사업방법서 모아둔 폴더 없음")
        if not os.path.exists(cls.working_dir):
            os.mkdir(path=cls.working_dir)
        if not os.path.exists(cls.output_dir):
            os.mkdir(path=cls.output_dir)

    def set_ctr_mapping(cls) -> dict:
        """
        주계약, 독립특약 Mapping 엑셀파일을 읽음
        """
        df = pd.read_excel(cls.list_file_path, sheet_name=cls.sheet_name)
        ctr_mapping = {}
        # main_ctr : 주계약 / dep_ctr : 독립특약
        for main_ctr in set(df['주계약'].values):
            ctr_mapping[main_ctr] = list(
                df.loc[df['주계약'] == main_ctr]['독립특약'].values)
        cls.prod_mapping = ctr_mapping

    def set_filename_mapping(cls) -> dict:
        # mapping 정보에서 단위상품이력코드 list를 추출
        prod_codes = set()
        for (k, v) in cls.prod_mapping.items():
            prod_codes.add(k)
            for _v in v:
                prod_codes.add(_v)
        prod_codes = list(prod_codes)
        # 실제 파일명을 가져옴
        filenames = [f.replace(".hwp", "")
                     for f in os.listdir(cls.hwp_doc_dir)]
        # mapping
        for prod_code in prod_codes:
            for filename in filenames:
                if filename.startswith(cls.prefix+prod_code):
                    cls.filename_mapping[prod_code] = filename
                    continue

    def modify_hwp_docs(cls):
        print("한글파일 색깔 변경 & pdf 변환 작업 中....")
        for k in tqdm(cls.filename_mapping.keys()):
            # input파일(한글문서) 경로
            hwp_doc_path = os.path.join(
                cls.hwp_doc_dir, f"{cls.filename_mapping[k]}.hwp")
            # 한글파일 열기
            hwp = cls.open_hwp_doc(hwp_doc_path)
            # 색깔 변경
            cls.change_text_color(hwp)
            # hwp, pdf 저장
            cls.save_hwp_doc(hwp, os.path.join(
                cls.working_dir, f"{cls.filename_mapping[k]}.hwp"))
            cls.convert_hwp_to_pdf(hwp, os.path.join(
                cls.working_dir, f"{cls.filename_mapping[k]}.pdf"))
            # 한글파일 닫기
            hwp.Quit()

    def merge_pdf_files_by_mapping(cls):
        """
        list에 매핑에 맞게 pdf 파일들을 병합함
        """
        print("pdf 문서 병합 中...")
        for (k, v) in tqdm(cls.prod_mapping.items()):
            # 병합할 pdf 파일 경로 list
            pdf_files = [f"{cls.filename_mapping[k]}.pdf"] + \
                [f"{cls.filename_mapping[_v]}.pdf" for _v in v]
            pdf_files = [os.path.join(cls.working_dir, pf) for pf in pdf_files]
            # 병합된 pdf 파일 저장 경로
            save_path = os.path.join(
                cls.output_dir, f"{cls.filename_mapping[k]}{cls.suffix}.pdf")
            # pdf 병합
            cls.merge_pdf(pdf_files, save_path)


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='옵션')
    parser.add_argument('--hwp', help='사업방법서 파일을 모아둔 폴더의 경로',
                        default="./input/hwp")
    parser.add_argument(
        '--excel', help='독립특약, 주계약 매핑을 정리한 엑셀파일 경로', default="./input/list.xlsx")
    parser.add_argument(
        '--sheet', help='독립특약, 주계약 매핑을 정리한 엑셀파일의 시트명', default="list")
    parser.add_argument('--working', help='작업파일을 저장할 폴더 경로',
                        default="./working")
    parser.add_argument('--output', help='결과를 저장할 폴더 경로', default="./output")

    args = parser.parse_args()

    rpa = Rpa(
        hwp_doc_dir=args.hwp,
        list_file_path=args.excel,
        sheet_name=args.sheet,
        output_dir=args.working,
        working_dir=args.output
    )

    try:
        rpa.main()
    except:
        print("ERROR!!!")
        sys.exit()
