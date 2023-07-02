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
    Dim qxType As Long                      ' 위험률유형
    Dim q As Double                         ' 위험률값
    Dim div1, div2, div3, div4 As Long      ' 구분자
    Dim qxCode As String                    ' 위험률코드
    Dim arr() As Double
    
    Set QxDict = CreateObject("Scripting.Dictionary")
    Set QxTypeDict = CreateObject("Scripting.Dictionary")
    
    With ThisWorkbook.Worksheets("RiskRate").Range("TAB_QX")
        For rr = 1 To .Rows.Count
            Application.StatusBar = rr & " /" & .Rows.Count
            
            qxCode = .Cells(rr, 1)
            qxType = .Cells(rr, 3)
            
            If Not QxTypeDict.exists(qxCode) Then QxTypeDict.Add qxCode, qxType
            
            ' Dictionary에 각 위험률 유형에 맞는 크기의 배열 집어넣기
            If Not QxDict.exists(qxCode) Then
                Select Case qxType
                    ' 단일률
                    Case 0
                        ReDim arr(0)
                        QxDict.Add qxCode, arr
                    
                    ' 성별
                    Case 1
                        ReDim arr(1 To 2)
                        QxDict.Add qxCode, arr
                    
                    ' 상해급수 (1~3급,단일률)
                    Case 2
                        ReDim arr(1 To 4)
                        QxDict.Add qxCode, arr
                    
                    ' 성별/상해급수
                    Case 3
                        ReDim arr(1 To 2, 1 To 4)
                        QxDict.Add qxCode, arr
                    
                    ' 연령율
                    Case 4
                        ReDim arr(110)
                        QxDict.Add qxCode, arr
                
                    ' 성별/연령율
                    Case 5
                        ReDim arr(1 To 2, 110)
                        QxDict.Add qxCode, arr
                
                
                    ' 성별/상해급수/연령율
                    Case 6
                        ReDim arr(1 To 2, 1 To 3, 110)
                        QxDict.Add qxCode, arr
                    
                    ' Error
                    Case Else
                        Stop
                
                End Select
            
            End If
            
        Next rr
        
        ' 위험률 값 집어넣기
        For rr = 1 To .Rows.Count
            
            qxCode = .Cells(rr, 1)
            div1 = .Cells(rr, 4)
            div2 = .Cells(rr, 5)
            div3 = .Cells(rr, 6)
            div4 = .Cells(rr, 7)
            q = .Cells(rr, 8)
            
            Select Case qxType
                ' 단일률
                Case 0
                    QxDict(qxCode) = q
                
                ' 성별
                Case 1
                    QxDict(qxCode)(div1) = q
                
                ' 상해급수 (1~3급,단일률)
                Case 2
                    QxDict(qxCode)(div1) = q
                
                ' 성별/상해급수
                Case 3
                    QxDict(qxCode)(div1, div2) = q
                
                ' 연령율
                Case 4
                    QxDict(qxCode)(div1) = q
            
                ' 성별/연령율
                Case 5
                    QxDict(qxCode)(div1, div2) = q
            
                ' 성별/상해급수/연령율
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
            
            seq = .Cells(rr, 1)                     ' 담보순번
            NumBenefit = .Cells(rr, 3)              ' 급부개수
            AMT = .Cells(rr, 4)
            
            ' 탈퇴위험률코드
            For nn = 0 To NumBenefit
                ExCode(nn) = .Cells(rr, nn + 5)
            Next nn
            
            ' 급부위험률코드
            For nn = 1 To NumBenefit
                BxCode(nn) = .Cells(rr, nn + 25)
            Next nn
            
            ' 지급률코드
            For nn = 1 To NumBenefit
                PayRate(nn) = .Cells(rr, nn + 45)
            Next nn
    
            ' 1차년도 감액률
            For nn = 1 To NumBenefit
                ReduceRate1(nn) = .Cells(rr, nn + 65)
            Next nn
                
            ' 2차년도 감액률
            For nn = 1 To NumBenefit
                ReduceRate2(nn) = .Cells(rr, nn + 85)
            Next nn
                
            ' 부담보
            For nn = 1 To NumBenefit
                NonCov(nn) = .Cells(rr, nn + 105)
            Next nn
            
            ' 저장
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
