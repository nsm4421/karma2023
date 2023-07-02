Attribute VB_Name = "G_Sample"
Option Explicit


Sub PrintSample()

    Dim stdRng As Range
    Dim numOffset As Long
    
    Call InitialSetUp
    
    ' Input ��Ʈ�� �ִ� ���� �����ϱ�
    With ThisWorkbook.Worksheets("Input")
        seq = .Range("seq")
        jong = .Range("jong")
        hyung = .Range("hyung")
        re = .Range("re")
        reTerm = .Range("reTerm")
        sex = .Range("sex")
        x = .Range("x")
        Pn = .Range("Pn")
        PnCk = .Range("PnCk")
        Pm = .Range("Pm")
        PmCk = .Range("PmCk")
        ir = .Range("ir")
        ip = .Range("ip")
        exp_i = .Range("exp_i")
    End With
    
    ' PV ����ϱ�
    Call SetUp
    Call CalcCommuation
    Call CalcPV

    ' Sample ��Ʈ�� ���
    With ThisWorkbook.Worksheets("Sample")
    
        Set stdRng = .Range("A1")
        
        ' t
        stdRng.offset(0, numOffset) = "t"
        For tt = 0 To 110
            stdRng.offset(tt + 1, numOffset) = tt
        Next tt
    
        ' x+t
        numOffset = numOffset + 1
        stdRng.offset(0, numOffset) = "x+t"
        For tt = 0 To 110
            stdRng.offset(tt + 1, numOffset) = x + tt
        Next tt
    
        ' Dx
        For nn = 0 To NumBenefit
            numOffset = numOffset + 1
            stdRng.offset(0, numOffset) = "Dx(" & nn & ")"
            For tt = x To x + PPn
                stdRng.offset(tt + 1 - x, numOffset) = Dx(nn, tt)
            Next tt
        Next nn
    
        ' D'x
        numOffset = numOffset + 1
        stdRng.offset(0, numOffset) = "D'x"
        For tt = x To x + PPn
            stdRng.offset(tt + 1 - x, numOffset) = DxPrime(tt)
        Next tt
        
        ' Cx
        For nn = 1 To NumBenefit
            numOffset = numOffset + 1
            stdRng.offset(0, numOffset) = "Cx(" & nn & ")"
            For tt = x To x + PPn
                stdRng.offset(tt + 1 - x, numOffset) = Cx(nn, tt)
            Next tt
        Next nn
    
        ' Mx
        For nn = 1 To NumBenefit
            numOffset = numOffset + 1
            stdRng.offset(0, numOffset) = "Mx(" & nn & ")"
            For tt = x To x + PPn
                stdRng.offset(tt + 1 - x, numOffset) = Mx(nn, tt)
            Next tt
        Next nn
    
        ' SUMx
        numOffset = numOffset + 1
        stdRng.offset(0, numOffset) = "SUMx"
        For tt = x To x + PPn
            stdRng.offset(tt + 1 - x, numOffset) = SUMx(tt)
        Next tt
        
        ' �غ��
        numOffset = numOffset + 1
        stdRng.offset(0, numOffset) = "tVx"
        For tt = x To x + PPn
            stdRng.offset(tt + 1 - x, numOffset) = tVx(tt)
        Next tt
        
        numOffset = numOffset + 1
        stdRng.offset(0, numOffset) = "tVx(���Աݾ׹ݿ�)"
        For tt = x To x + PPn
            stdRng.offset(tt + 1 - x, numOffset) = Application.Round(tVx(tt) * AMT, 0)
        Next tt
        
        ' �����
        numOffset = numOffset + 1
        stdRng.offset(0, numOffset) = "�����������"
        stdRng.offset(1, numOffset) = NP_annual
        
        numOffset = numOffset + 1
        stdRng.offset(0, numOffset) = "�����������(���Աݾ׹ݿ�)"
        stdRng.offset(1, numOffset) = Application.Round(NP_annual * AMT, 0)
        
        numOffset = numOffset + 1
        stdRng.offset(0, numOffset) = "�����������"
        stdRng.offset(1, numOffset) = NP_month
        
        numOffset = numOffset + 1
        stdRng.offset(0, numOffset) = "�����������(���Աݾ׹ݿ�)"
        stdRng.offset(1, numOffset) = Application.Round(NP_month * AMT, 0)
        
        numOffset = numOffset + 1
        stdRng.offset(0, numOffset) = "�������������"
        stdRng.offset(1, numOffset) = GP_annual
        
        numOffset = numOffset + 1
        stdRng.offset(0, numOffset) = "�������������(���Աݾ׹ݿ�)"
        stdRng.offset(1, numOffset) = Application.Round(GP_annual * AMT, 0)
        
        numOffset = numOffset + 1
        stdRng.offset(0, numOffset) = "�������������"
        stdRng.offset(1, numOffset) = GP_month
        
        numOffset = numOffset + 1
        stdRng.offset(0, numOffset) = "�������������(���Աݾ׹ݿ�)"
        stdRng.offset(1, numOffset) = Application.Round(GP_month * AMT, 0)
        
    
    End With

End Sub
