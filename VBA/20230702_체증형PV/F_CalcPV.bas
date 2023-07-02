Attribute VB_Name = "F_CalcPV"
Option Explicit

Sub CalcPV()

    Call CalcP
    Call CalcV

End Sub


Sub CalcP()

    Select Case jong
        
        ' ∆Ú¡ÿ≥≥
        Case 1
            NP_annal = SUMx(x) / NxStar1(x)
            NP_month = SUMx(x) / NxStar12(x)
            GP_annual = SUMx(x) / (1 * ((1 - beta(1) - ce(1) - gamma) * NxStar1(x) - alpha2 * DxPrime(x)))
            GP_month = SUMx(x) / (12 * ((1 - beta(1) - ce(1) - gamma) * NxStar12(x) - alpha2 * DxPrime(x)))
            
        ' √º¡ı«¸
        Case 2
            Dim sigma As Double
            For nn = 1 To PPm / ip - 1
                sigma = sigma + NxStar1(x + ip * nn)
            Next nn
            NP_annual = SUMx(x) / (1 * (NxStar1(x) + sigma))
            GP_annual = SUMx(x) / (1 * ((1 - beta(1) - ce(1) - gamma) * NxStar1(x) - alpha2 * DxPrime(x))) _
                    * NxStar1(x) / (NxStar1(x) + sigma)
                    
            sigma = 0
            For nn = 1 To PPm / ip - 1
                sigma = sigma + NxStar12(x + ip * nn)
            Next nn
            NP_month = SUMx(x) / (12 * (NxStar12(x) + sigma))
            GP_annual = SUMx(x) / (12 * ((1 - beta(1) - ce(1) - gamma) * NxStar12(x) - alpha2 * DxPrime(x))) _
                    * NxStar12(x) / (NxStar12(x) + sigma)
                
        ' Error
        Case Else
            Stop
    
    End Select

End Sub


Sub CalcV()

    Select Case jong
        
        ' ∆Ú¡ÿ≥≥
        Case 1
            
            ' ≥≥¿‘¡ﬂ
            For tt = 0 To PPm - 1
                tVx(tt) = (SUMx(tt) - NP_annual * NxStar1(tt)) / Dx(0, tt)
            Next tt
                
            ' øœ≥≥ »ƒ
            For tt = PPm To PPn
                tVx(tt) = SUMx(tt) / Dx(0, tt)
            Next tt
                
        ' √º¡ı«¸
        Case 2
        
            ' ≥≥¿‘¡ﬂ
            For tt = 0 To PPm - 1
                tVx(tt) = (SUMx(tt) - NP_annual * NxSigma(tt)) / Dx(0, tt)
            Next tt
                
            ' øœ≥≥ »ƒ
            For tt = PPm To PPn
                tVx(tt) = SUMx(tt) / Dx(0, tt)
            Next tt
        
        ' Error
        Case Else
            Stop
    
    End Select

End Sub
