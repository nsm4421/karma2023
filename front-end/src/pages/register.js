import axios from "axios";
import { useState } from "react";

export default function Register(){
   
    /** States */
    const [username, setUsername] = useState("");
    const [nickname, setNickname] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [isLoading, setIsLoading] = useState(false);

    const handleUsername = (e) => {setUsername(e.target.value)};
    const handleNickname = (e) => {setNickname(e.target.value)};
    const handleEmail = (e) => {setEmail(e.target.value)};
    const handlePassword = (e) => {setPassword(e.target.value)};

    const handleSubmit = async () => {
        const endPoint = '/api/user/register';
        setIsLoading(true);
        const data = {username, nickname, email, password};
        const config = {};
        await axios.post(endPoint, data, config)
            .then(res=>res.data.data)
            .then((data)=>{
                alert(`${data.nickname}님 회원가입에 성공하였습니다.`)
                // TODO : 회원가입 성공시 처리
            })
            .catch((err)=>{
                alert(err.response.data.message);
            })
            .finally(()=>{setIsLoading(false);});
    }

    return(
        <div>
            <h1>회원가입</h1>

            <div>
                <label>유저명</label>
                <input placeholder="로그인 시에 사용할 유저명" onChange={handleUsername}/>
            </div>

            <div>
                <label>닉네임</label>
                <input placeholder="닉네임" onChange={handleNickname}/>
            </div>

            <div>
                <label>이메일</label>
                <input placeholder="이메일" type="email" onChange={handleEmail}/>
            </div>

            <div>
                <label>비밀번호</label>
                <input placeholder="패스워드" type="password" onChange={handlePassword}/>
            </div>

            <button onClick={handleSubmit} disabled={isLoading}>회원가입하기</button>

        </div>
    )
}