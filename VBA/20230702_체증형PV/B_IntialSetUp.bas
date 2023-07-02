Attribute VB_Name = "B_IntialSetUp"
Option Explicit

Sub InitialSetUp()

    exp_i = 0.0275
    exp_v = 1 / (1 + exp_i)
    
    Call SetQxDict
    Call SetCovDict
    Call SetExpDict

End Sub

Sub SetQxDict()
    
    Dim rr As Long
    Dim qxType As Long                      ' ���������
    Dim q As Double                         ' �������
    Dim div1, div2, div3, div4 As Long      ' ������
    Dim qxCode As String                    ' ������ڵ�
    Dim arr() As Double
    
    Set QxDict = CreateObject("Scripting.Dictionary")
    Set QxTypeDict = CreateObject("Scripting.Dictionary")
    
    With ThisWorkbook.Worksheets("RiskRate").Range("TAB_QX")
        For rr = 1 To .Rows.Count
            Application.StatusBar = rr & " /" & .Rows.Count
            
            qxCode = .Cells(rr, 1)
            qxType = .Cells(rr, 3)
            
            If Not QxTypeDict.exists(qxCode) Then QxTypeDict.Add qxCode, qxType
            
            ' Dictionary�� �� ����� ������ �´� ũ���� �迭 ����ֱ�
            If Not QxDict.exists(qxCode) Then
                Select Case qxType
                    ' ���Ϸ�
                    Case 0
                        ReDim arr(0)
                        QxDict.Add qxCode, arr
                    
                    ' ����
                    Case 1
                        ReDim arr(1 To 2)
                        QxDict.Add qxCode, arr
                    
                    ' ���ر޼� (1~3��,���Ϸ�)
                    Case 2
                        ReDim arr(1 To 4)
                        QxDict.Add qxCode, arr
                    
                    ' ����/���ر޼�
                    Case 3
                        ReDim arr(1 To 2, 1 To 4)
                        QxDict.Add qxCode, arr
                    
                    ' ������
                    Case 4
                        ReDim arr(110)
                        QxDict.Add qxCode, arr
                
                    ' ����/������
                    Case 5
                        ReDim arr(1 To 2, 110)
                        QxDict.Add qxCode, arr
                
                
                    ' ����/���ر޼�/������
                    Case 6
                        ReDim arr(1 To 2, 1 To 3, 110)
                        QxDict.Add qxCode, arr
                    
                    ' Error
                    Case Else
                        Stop
                
                End Select
            
            End If
            
        Next rr
        
        ' ����� �� ����ֱ�
        For rr = 1 To .Rows.Count
            
            qxCode = .Cells(rr, 1)
            div1 = .Cells(rr, 4)
            div2 = .Cells(rr, 5)
            div3 = .Cells(rr, 6)
            div4 = .Cells(rr, 7)
            q = .Cells(rr, 8)
            
            Select Case qxType
                ' ���Ϸ�
                Case 0
                    QxDict(qxCode) = q
                
                ' ����
                Case 1
                    QxDict(qxCode)(div1) = q
                
                ' ���ر޼� (1~3��,���Ϸ�)
                Case 2
                    QxDict(qxCode)(div1) = q
                
                ' ����/���ر޼�
                Case 3
                    QxDict(qxCode)(div1, div2) = q
                
                ' ������
                Case 4
                    QxDict(qxCode)(div1) = q
            
                ' ����/������
                Case 5
                    QxDict(qxCode)(div1, div2) = q
            
                ' ����/���ر޼�/������
                Case 6
                    QxDict(qxCode)(div1, div2, div3) = q
                    
                ' Error
                Case Else
                    Stop
            
            End Select
            
        Next rr
    End With
    
    Application.StatusBar = False

End Sub

Sub SetCovDict()
    
    Set CovDict = CreateObject("Scripting.Dictionary")
    
    With Worksheets("Coverage").Range("TAB_COV")
        For rr = 1 To .Rows.Count
        
            Erase ExCode, BxCode
            
            seq = .Cells(rr, 1)                     ' �㺸����
            NumBenefit = .Cells(rr, 3)              ' �޺ΰ���
            AMT = .Cells(rr, 4)
            
            ' Ż��������ڵ�
            For nn = 0 To NumBenefit
                ExCode(nn) = .Cells(rr, nn + 5)
            Next nn
            
            ' �޺�������ڵ�
            For nn = 1 To NumBenefit
                BxCode(nn) = .Cells(rr, nn + 25)
            Next nn
            
            ' ���޷��ڵ�
            For nn = 1 To NumBenefit
                PayRate(nn) = .Cells(rr, nn + 45)
            Next nn
    
            ' 1���⵵ ���׷�
            For nn = 1 To NumBenefit
                ReduceRate1(nn) = .Cells(rr, nn + 65)
            Next nn
                
            ' 2���⵵ ���׷�
            For nn = 1 To NumBenefit
                ReduceRate2(nn) = .Cells(rr, nn + 85)
            Next nn
                
            ' �δ㺸
            For nn = 1 To NumBenefit
                NonCov(nn) = .Cells(rr, nn + 105)
            Next nn
            
            ' ����
            CovDict.Add seq & "," & "AMT", AMT
            CovDict.Add seq & "," & "NumBenefit", NumBenefit
            CovDict.Add seq & "," & "ExCode", ExCode
            CovDict.Add seq & "," & "BxCode", BxCode
            CovDict.Add seq & "," & "PayRate", PayRate
            CovDict.Add seq & "," & "ReduceRate1", ReduceRate1
            CovDict.Add seq & "," & "ReduceRate2", ReduceRate2
            CovDict.Add seq & "," & "NonCov", NonCov

        Next rr
    End With
End Sub

Sub SetExpDict()

    Dim expKey As String

    With Worksheets("Expense").Range("TAB_EXP")
        For rr = 1 To .Rows.Count
        
            Application.StatusBar = rr & " / " & .Rows.Count
            
            seq = .Cells(rr, 1)
            jong = .Cells(rr, 2)
            hyung = .Cells(rr, 3)
            re = .Cells(rr, 4)
            reTerm = .Cells(rr, 5)
            
            expKey = seq & "," & jong & "," & hyung & "," & re & "," & reTerm & "," & Pn & "," & PnCk & "," & Pm & "," & PmCk
            
            ' Error
            If ExpDict.exists(expKey) Then Stop
            
            alpha(1) = .Cells(rr, 11)
            alpha(2) = .Cells(rr, 12)
            alpha(3) = .Cells(rr, 13)
            alpha(4) = .Cells(rr, 14)
            
            beta(1) = .Cells(rr, 15)
            beta(2) = .Cells(rr, 16)
            beta(3) = .Cells(rr, 17)
            
            gamma = .Cells(rr, 18)
            
            ce(1) = .Cells(rr, 19)
            ce(2) = .Cells(rr, 20)
            
            ExpDict.Add expKey & "," & "alpha", .alpha
            ExpDict.Add expKey & "," & "beta", .beta
            ExpDict.Add expKey & "," & "gamma", gamma
            ExpDict.Add expKey & "," & "ce", ce
            
            Erase alpha1, beta, ce
            
        Next rr
    End With

    Application.StatusBar = False
    
End Sub
