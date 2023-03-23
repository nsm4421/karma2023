import PrimarySearchAppBar from '../layout/nav';

export default function Nav(){

    // TODO : 로그인 상태에 따라 보이는 메뉴를 다르게 하기
    const navItems = [
        {label:'홈', href:'/'},
        {label:'회원가입', href:'/register'},
        {label:'로그인', href:'/login'},
        {label:'로그아웃', href:'/logout'},
        {label:'게시글', href:'/article'},
    ]

    return (
        <PrimarySearchAppBar navItems={navItems}/>
    )
}