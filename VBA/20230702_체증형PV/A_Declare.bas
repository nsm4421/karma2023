Attribute VB_Name = "A_Declare"
' 전역변수 설정
Option Explicit

    Public QxDict As Object             ' 위험률Dict
    Public QxTypeDict As Object         ' 위험률유형 Dict
    Public CovDict As Object            ' 급부Dict
    Public seq As Long                  ' 담보순번
    Public jong As Integer              ' 종
    Public hyung As Integer             ' 형
    Public re As Integer                ' 신규갱신
    Public reTerm As Long               ' 갱신주기
    Public sex As Integer               ' 성별
    Public x As Long                    ' 연령
    Public injure As Integer            ' 상해급수
    Public ip As Integer                ' 체증주기
    Public ir As Double                 ' 체증률
    Public exp_i As Double              ' 예정이율
    Public exp_v As Double              ' 현가계수
    Public Pn As Long                   ' 만기
    Public Pm As Long                   ' 납기
    Public PPn As Long                  ' 실만기
    Public PPm As Long                  ' 실납기
    Public PnCk As Integer              ' 만기구분 - 1:연만기 / 2:세만기
    Public PmCk As Integer              ' 납기구분 - 1:연납기 / 2:세납기
    Public AMT As Long
    
    ' 기수계산에 필요한 변수
    Public ii, nn, tt As Long
    Public NumBenefit As Integer            ' 급부 개수
    Public PayRate(1 To 20) As Double       ' 지급률
    Public NonCov(1 To 20) As Integer       ' 부담보기간
    Public ReduceRate1(1 To 20) As Double   ' 1차년도 감액률
    Public ReduceRate2(1 To 20) As Double   ' 2차년도 감액률
    Public ExCode(20) As String             ' 탈퇴율 코드
    Public Ex(0 To 20, 110)                 ' 탈퇴율
    Public BxCode(1 To 20)                  ' 급부율 코드
    Public Bx(1 To 20, 110)                 ' 급부율
    Public Gx(110)                          ' 납입면제율
    
    ' 기수식
    Public Dx(20, 110) As Double
    Public DxPrime(110) As Double
    Public Cx(1 To 20, 110) As Double
    Public Nx(20, 110) As Double
    Public NxPrime(110) As Double
    Public Mx(1 To 20, 110) As Double
    Public SUMx(110) As Double
    Public NxStar1(110) As Double           ' N'※(1) = N'x+t - N'x+m
    Public NxStar12(110) As Double          ' N'※(12) = N'x+t - N'x+m - 11/24*(D'x+t-D'x+m)
    Public NxSigma(110) As Double           ' NxSigma(x+t) = N'x+t-N'x+m + ir*((N'x+ip-N'x+m)+(N'x+2ip-N'x+m)+....+(N'x+m-ip-N'x+m))
    
    ' 사업비
    Public alpha(1 To 4) As Double          ' 신계약비
    Public beta(1 To 3) As Double           ' 유지비
    Public gamma As Double                  ' 수금비
    Public ce(1 To 2) As Double             ' 손해조사비
    
    ' PV
    Public NP_annual As Double              ' 연납순보험료
    Public GP_annaul As Double              ' 연납영업보험료
    Public NP_month As Double               ' 월납순보험료
    Public GP_month As Double               ' 월납영업보험료
    Public tVx(110) As Double               ' 준비금
    Public tWx(110) As Double               ' 해약환급금
    
    ' 기타
    Public isValidLine  As Boolean          ' 해당 라인을 추출하는지 여부
    
    
    
    
    
    
    
    
