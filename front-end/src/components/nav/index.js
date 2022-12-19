import { Link } from 'react-router-dom';

const Nav = ()=>{
    return (
        <>
            <nav>
               <Link to="/">홈화면</Link>
               <Link to="/post">포스팅</Link>
               <Link to="/register">회원가입</Link>
               <Link to="/login">로그인</Link>
               <Link to="/logout">로그아웃</Link>
            </nav>
        </>
    );
}

export default Nav;