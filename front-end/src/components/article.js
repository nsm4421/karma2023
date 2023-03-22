import axios from "axios"
import { useNavigate } from "react-router-dom";

export default function Article({article, getArticle}){

    const navigator = useNavigate();

    const moveToModifyPage = () => {
        navigator(`/article/modify/${article.articleId}`);
    }

    /**
     * 게시글 삭제 성공 시, Article List 다시 불러오기
     * TODO : 다시 불러오는 페이지는 현재 보고 있는 페이지를 불러오도록 수정하기
     */
    const deleteArticle = () => {
        const endPoint = `/api/article/${article.articleId}`;
        axios.delete(endPoint).then((res)=>{
            console.log(res);
            getArticle();
        }).catch((err)=>{
            console.log(err);
        });
    }

    return (
        <div>
            <div>
                <label>제목</label>
                <span>{article.title}</span>
            </div>
            <div>
                <label>작성자</label>
                <span>{article.userAccountDto.nickname}</span>
            </div>
            {/* TODO : 내가 쓴 게시물만 아래 버튼들이 보이게 하기 */}
            <div>
                <button onClick={moveToModifyPage}>수정하기</button>
                <button onClick={deleteArticle}>삭제하기</button>
            </div>
            <div>
                <label>본문</label>
                <span>{article.content}</span>
            </div>
            <div>
                <label>해시태그</label>
                {
                    article.hashtags.map((h, i)=>{
                        return(
                            <div key={i}>
                                <span>#{h}</span>
                            </div>
                        )
                    })
                }
            </div>
            <div>
                <label>작성시간</label>
                <span>{article.createdAt}</span>
                <br/>
                <label>수정시간</label>
                <span>{article.modifiedAt}</span>
            </div>
        </div>
    )
}