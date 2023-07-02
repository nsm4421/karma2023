Attribute VB_Name = "C_SetUp"
Option Explicit

' 미리 세팅되어야 하는 변수
' QxDict, QxTypeDict, CovDict
' x, Pn, PnCk, Pm, PmCk

' 세팅해주는 변수
' PPn, PPm, Ex, Bx, Gx, 사업비
Sub SetUp()

    PPn = IIf(PnCk = 1, Pn, Pn - x)
    PPm = IIf(PmCk = 1, Pm, Pm - x)

    Erase Ex, Bx, Gx
    Call ExSetUp
    Call BxSetUp
    Call GxSetUp

End Sub

' Ex(탈퇴율)변수 세팅
Sub ExSetUp()
  
    For nn = 0 To NumBenefit
        For tt = x To x + PPn
            Ex(nn, tt) = getQ(ExCode(nn), tt)
        Next tt
    Next nn
    
End Sub

' Bx(급부율)변수 세팅
Sub BxSetUp()

    For nn = 1 To NumBenefit
        If qxKey = "" Then Stop     ' <--- Error
        For tt = x To x + PPn
            Bx(nn, tt) = QxDict(BxCode(nn), tt)
        Next tt
    Next nn
    
End Sub

' 납입면제율
Sub GxSetUp()

    For tt = x To x + PPn
        Gx(tt) = Ex(tt)
    Next tt

End Sub



Function getQ(qxCode As String, Optional tt As Long)

    Select Case qxType
        ' 단일률
        Case 0
            getQ = QxDict(qxCode)
        
        ' 성별
        Case 1
            getQ = QxDict(qxCode)(sex)
        
        ' 상해급수 (1~3급,단일률)
        Case 2
            getQ = QxDict(qxCode)(injure)
        
        ' 성별/상해급수
        Case 3
            getQ = QxDict(qxCode)(sex, injure)
        
        ' 연령율
        Case 4
            getQ = QxDict(qxCode)(tt)
            
        ' 성별/연령율
        Case 5
            getQ = QxDict(qxCode)(sex, tt)
    
        ' 성별/상해급수/연령율
        Case 6
            getQ = QxDict(qxCode)(sex, injure, tt)
        
        ' Error
        Case Else
            Stop
    
    End Select

End Function


Sub ExpSetUp()

    Dim expKey As String
   
    expKey = seq & "," & jong & "," & hyung & "," & re & "," & reTerm & "," & Pn & "," & PnCk & "," & Pm & "," & PmCk
        
    alpha1 = ExpDict(expKey & "alpha")(1)
    alpha2 = ExpDict(expKey & "alpha")(2)
    alpha3 = ExpDict(expKey & "alpha")(3)
    alpha4 = ExpDict(expKey & "alpha")(4)
    
    beta1 = ExpDict(expKey & "beta")(1)
    beta2 = ExpDict(expKey & "beta")(2)
    beta3 = ExpDict(expKey & "beta")(3)
    
    gamma = ExpDict(expKey & "gamma")
    
    ce(1) = ExpDict(expKey & "ce")(1)
    ce(2) = ExpDict(expKey & "ce")(2)
    
End Sub
