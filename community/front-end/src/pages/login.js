import { AccountBoxRounded, Key, Visibility, VisibilityOff } from "@mui/icons-material";
import { Button, FormControl, IconButton, InputAdornment, InputLabel, OutlinedInput, TextField } from "@mui/material";
import axios from "axios";
import { useState } from "react"
import { useNavigate } from "react-router-dom";
import { useRecoilState } from "recoil";
import { userState } from "..";
import { getNicknameApi, loginApi } from "../api/authApi";

export default function Login(){

    const navigator = useNavigate();
    const [user, setUser] = useRecoilState(userState);
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [showPassword, setShowPassword] = useState("");
    const [isLoading, setIsLoading] = useState(false);
    var formData = new FormData();

    const handleUsername = (e) => {
        setUsername(e.target.value);
    };

    const handlePassword = (e) => {
        setPassword(e.target.value);
    };

    const handleClickShowPassword = () => {setShowPassword(!showPassword);};
    const handleMouseDownPassword = (e) => {e.preventDefault();};

    const handleSubmit = async () => {
        const endPoint = "/api/login";        
        formData.append("username", username);
        formData.append("password", password);
        setIsLoading(true);
        await loginApi(formData,()=>{},()=>{});
        await checkLoginSuccess();
        setIsLoading(false);
    }

    const checkLoginSuccess = async ()=>{
        await getNicknameApi(
            (res)=>{
                const nickname = res.data;
                if (nickname){
                    setUser({...user, nickname:res.data});
                    navigator("/article");
                    return;
                }
                alert("로그인 실패");
            },
            (err)=>{
                alert("로그인 실패");
                console.log(err)
            }
        )        
    }

    return (
        <div>
        
            {/* 유저명 */}
            <TextField
                margin="normal"
                required
                fullWidth
                label="유저명"
                placeholder='로그인 시에 사용할 유저명'
                autoFocus
                InputProps={{ startAdornment: <InputAdornment position="start"><AccountBoxRounded /></InputAdornment>, }}
                value={username}
                onChange={handleUsername}
            />

            {/* 비밀번호 */}
            <FormControl fullWidth variant="outlined" margin="normal">
                <InputLabel htmlFor="outlined-adornment-password">비밀번호</InputLabel>
                <OutlinedInput
                    value={password}
                    onChange={handlePassword}
                    type={showPassword ? 'text' : 'password'}
                    startAdornment={
                        <InputAdornment position="start">
                            <Key />
                        </InputAdornment>
                    }
                    placeholder="1q2w3e4r!!"
                    endAdornment={
                        <InputAdornment position="end">
                            <IconButton
                                aria-label="toggle password visibility"
                                onClick={handleClickShowPassword}
                                onMouseDown={handleMouseDownPassword}
                                edge="end"
                            >
                                {showPassword ? <VisibilityOff /> : <Visibility />}
                            </IconButton>
                        </InputAdornment>
                    }
                    label="비밀번호"
                />
            </FormControl>

            <Button disabled={isLoading} onClick={handleSubmit}>로그인</Button>
        </div>
    )
}