Attribute VB_Name = "A_Declare"
' �������� ����
Option Explicit

    Public QxDict As Object             ' �����Dict
    Public QxTypeDict As Object         ' ��������� Dict
    Public CovDict As Object            ' �޺�Dict
    Public seq As Long                  ' �㺸����
    Public jong As Integer              ' ��
    Public hyung As Integer             ' ��
    Public re As Integer                ' �ű԰���
    Public reTerm As Long               ' �����ֱ�
    Public sex As Integer               ' ����
    Public x As Long                    ' ����
    Public injure As Integer            ' ���ر޼�
    Public ip As Integer                ' ü���ֱ�
    Public ir As Double                 ' ü����
    Public exp_i As Double              ' ��������
    Public exp_v As Double              ' �������
    Public Pn As Long                   ' ����
    Public Pm As Long                   ' ����
    Public PPn As Long                  ' �Ǹ���
    Public PPm As Long                  ' �ǳ���
    Public PnCk As Integer              ' ���ⱸ�� - 1:������ / 2:������
    Public PmCk As Integer              ' ���ⱸ�� - 1:������ / 2:������
    Public AMT As Long
    
    ' �����꿡 �ʿ��� ����
    Public ii, nn, tt As Long
    Public NumBenefit As Integer            ' �޺� ����
    Public PayRate(1 To 20) As Double       ' ���޷�
    Public NonCov(1 To 20) As Integer       ' �δ㺸�Ⱓ
    Public ReduceRate1(1 To 20) As Double   ' 1���⵵ ���׷�
    Public ReduceRate2(1 To 20) As Double   ' 2���⵵ ���׷�
    Public ExCode(20) As String             ' Ż���� �ڵ�
    Public Ex(0 To 20, 110)                 ' Ż����
    Public BxCode(1 To 20)                  ' �޺��� �ڵ�
    Public Bx(1 To 20, 110)                 ' �޺���
    Public Gx(110)                          ' ���Ը�����
    
    ' �����
    Public Dx(20, 110) As Double
    Public DxPrime(110) As Double
    Public Cx(1 To 20, 110) As Double
    Public Nx(20, 110) As Double
    Public NxPrime(110) As Double
    Public Mx(1 To 20, 110) As Double
    Public SUMx(110) As Double
    Public NxStar1(110) As Double           ' N'��(1) = N'x+t - N'x+m
    Public NxStar12(110) As Double          ' N'��(12) = N'x+t - N'x+m - 11/24*(D'x+t-D'x+m)
    Public NxSigma(110) As Double           ' NxSigma(x+t) = N'x+t-N'x+m + ir*((N'x+ip-N'x+m)+(N'x+2ip-N'x+m)+....+(N'x+m-ip-N'x+m))
    
    ' �����
    Public alpha(1 To 4) As Double          ' �Ű���
    Public beta(1 To 3) As Double           ' ������
    Public gamma As Double                  ' ���ݺ�
    Public ce(1 To 2) As Double             ' ���������
    
    ' PV
    Public NP_annual As Double              ' �����������
    Public GP_annaul As Double              ' �������������
    Public NP_month As Double               ' �����������
    Public GP_month As Double               ' �������������
    Public tVx(110) As Double               ' �غ��
    Public tWx(110) As Double               ' �ؾ�ȯ�ޱ�
    
    ' ��Ÿ
    Public isValidLine  As Boolean          ' �ش� ������ �����ϴ��� ����
    
    
    
    
    
    
    
    
