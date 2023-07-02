Attribute VB_Name = "C_SetUp"
Option Explicit

' �̸� ���õǾ�� �ϴ� ����
' QxDict, QxTypeDict, CovDict
' x, Pn, PnCk, Pm, PmCk

' �������ִ� ����
' PPn, PPm, Ex, Bx, Gx, �����
Sub SetUp()

    PPn = IIf(PnCk = 1, Pn, Pn - x)
    PPm = IIf(PmCk = 1, Pm, Pm - x)

    Erase Ex, Bx, Gx
    Call ExSetUp
    Call BxSetUp
    Call GxSetUp

End Sub

' Ex(Ż����)���� ����
Sub ExSetUp()
  
    For nn = 0 To NumBenefit
        For tt = x To x + PPn
            Ex(nn, tt) = getQ(ExCode(nn), tt)
        Next tt
    Next nn
    
End Sub

' Bx(�޺���)���� ����
Sub BxSetUp()

    For nn = 1 To NumBenefit
        If qxKey = "" Then Stop     ' <--- Error
        For tt = x To x + PPn
            Bx(nn, tt) = QxDict(BxCode(nn), tt)
        Next tt
    Next nn
    
End Sub

' ���Ը�����
Sub GxSetUp()

    For tt = x To x + PPn
        Gx(tt) = Ex(tt)
    Next tt

End Sub



Function getQ(qxCode As String, Optional tt As Long)

    Select Case qxType
        ' ���Ϸ�
        Case 0
            getQ = QxDict(qxCode)
        
        ' ����
        Case 1
            getQ = QxDict(qxCode)(sex)
        
        ' ���ر޼� (1~3��,���Ϸ�)
        Case 2
            getQ = QxDict(qxCode)(injure)
        
        ' ����/���ر޼�
        Case 3
            getQ = QxDict(qxCode)(sex, injure)
        
        ' ������
        Case 4
            getQ = QxDict(qxCode)(tt)
            
        ' ����/������
        Case 5
            getQ = QxDict(qxCode)(sex, tt)
    
        ' ����/���ر޼�/������
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
