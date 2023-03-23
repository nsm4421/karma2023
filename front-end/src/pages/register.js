import { useState } from "react";
import { useNavigate } from "react-router";
import { regsterApi } from "../api/authApi";

export default function Register(){

    const navigator = useNavigate();
   
    /** States */
    const [username, setUsername] = useState("");
    const [nickname, setNickname] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [description, setDescription] = useState("");
    const [isLoading, setIsLoading] = useState(false);

    const handleUsername = (e) => {setUsername(e.target.value)};
    const handleNickname = (e) => {setNickname(e.target.value)};
    const handleEmail = (e) => {setEmail(e.target.value)};
    const handlePassword = (e) => {setPassword(e.target.value)};
    const handleDescription = (e) => {setDescription(e.target.value)};

    const handleSubmit = async () => {
        const successCallback = (res) => {
            alert(res.data.message);
            navigator("/login");
        };
        const failureCallback = (err) => {
            alert("회원가입에 실패하였습니다.")
            console.log(err);
        };
        setIsLoading(true);
        await regsterApi(username, nickname, email, password, description, successCallback, failureCallback)
        setIsLoading(false);
    }

    return(
        <div>
            <h1>회원가입</h1>

            <div>
                <label>유저명</label>
                <input value={username} placeholder="로그인 시에 사용할 유저명" onChange={handleUsername}/>
            </div>

            <div>
                <label>닉네임</label>
                <input value={nickname} placeholder="닉네임" onChange={handleNickname}/>
            </div>

            <div>
                <label>이메일</label>
                <input value={email} placeholder="이메일" type="email" onChange={handleEmail}/>
            </div>

            <div>
                <label>비밀번호</label>
                <input value={password} placeholder="패스워드" type="password" onChange={handlePassword}/>
            </div>

            <div>
                <label>자기소개</label>
                <textarea value={description} placeholder="자기소개를 간단히 적어주세요" onChange={handleDescription}/>
            </div>

            <button onClick={handleSubmit} disabled={isLoading}>회원가입하기</button>

        </div>
    )
}