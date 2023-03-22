import axios from "axios";
import { useState } from "react"

export default function Login(){
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [isLoading, setIsLoading] = useState(false);

    const handleUsername = (e) => {setUsername(e.target.value);};
    const handlePassword = (e) => {setPassword(e.target.value);};
    const handleSubmit = async (e) => {
        setIsLoading(true);
        const endPoint = "/api/user/login";
        await axios
            .post(endPoint, {username, password})
            .then((res)=>{
                console.log(res);
            })
            .catch((err)=>{
                alert("오류발생");
                console.log(err);
            })
            .finally(()=>{
                setIsLoading(false);
            });
    }   

    return (
        <div>
            <div>
                <label>유저명</label>
                <input value={username} onChange={handleUsername} placeholder="유저명을 입력하세요"/>
            </div>
            <div>
                <label>비밀번호</label>
                <input value={password} onChange={handlePassword} type="password" placeholder="비밀번호를 입력하세요"/>
            </div>
            <button disabled={isLoading} onClick={handleSubmit}>로그인</button>
        </div>
    )
}