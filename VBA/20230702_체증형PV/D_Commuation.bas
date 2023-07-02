Attribute VB_Name = "D_Commuation"
Option Explicit


Sub CalcCommuation()

  ' 기수 초기화
    Erase Dx, DxPrime, Nx, NxPrime, Cx, Mx, SUMx, NxStar1, NxStar12, NxSigma, tVx
 
    ' Dx
    For nn = 0 To NumBenefit
        Dx(nn, x) = 10000
        Dx(nn, x + 1) = Dx(nn, x) * (1 - Ex(nn, tt) * (1 - NonCov(nn)) / 12) * exp_v
        For tt = x + 2 To x + PPn
            Dx(nn, tt + 1) = Dx(nn, tt) * (1 - Ex(nn, tt)) * exp_v
        Next tt
    Next nn

    ' D'x
    DxPrime(x) = 10000
    For tt = x + 1 To x + PPn
        DxPrime(tt + 1) = DxPrime(tt) * (1 - Gx(nn, tt))
    Next tt
    
    ' Cx
    For nn = 1 To NumBenefit
        For tt = x To x + PPn
            Cx(nn, tt) = Dx(nn, tt) * Bx(nn, tt) * (exp_v^(1 / 2))
        Next tt
    Next nn
    
    ' Nx
    For nn = 0 To NumBenefit
        For tt = x + PPn To x
            Nx(nn, tt) = Dx(nn, tt) + Nx(nn, tt + 1)
        Next tt
    Next nn
    
     ' Mx
    For nn = 1 To NumBenefit
        For tt = x + PPn To x
            Mx(nn, tt) = Cx(nn, tt) + Mx(nn, tt + 1)
        Next tt
    Next nn
    
    ' SUMx
    For nn = 1 To NumBenefit
         SUMx(x) = SUMx(x) + PayRate(nn) * (Mx(x) - Mx(x + PPn)) * (1 - ReduceRate1(nn))
         SUMx(x + 1) = SUMx(x + 1) + PayRate(nn) * (Mx(x + 1) - Mx(x + PPn)) * (1 - ReduceRate2(nn))
        For tt = x + 2 To x + PPn
            SUMx(tt) = SUMx(tt) + PayRate(nn) * (Mx(tt) - Mx(x + PPn))
        Next tt
    Next nn
        
    ' N'x
    For tt = x + PPn To x
        NxPrime(tt) = Dx(tt) + NxPrime(tt + 1)
    Next tt
    
    ' N※x
    For tt = x To x + PPm
        NxStar1(tt) = NxPrime(tt) - NxPrime(x + PPm)
        NxStar12(tt) = NxPrime(tt) - NxPrime(x + PPm) - 11 / 24 * (DxPrime(tt) - DxPrime(x + PPm))
    Next tt
    
    ' NxSigma
    If joing = 2 Then
        Dim sigma As Double
        For tt = x To x + PPm
            sigma = 0
            For nn = 0 To PPm
                sigma = sigma + IIf((nn Mod ip) = 0, 1, 0) * (NxPrime(x + ip * nn) - NxPrime(x + PPm))
            Next nn
            NxSigma(tt) = NxPrime(tt) - NxPrime(x + PPm) + ir * sigma
        Next tt
    End If

End Sub

