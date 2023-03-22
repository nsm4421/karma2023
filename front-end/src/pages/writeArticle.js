import axios from "axios";
import { useState } from "react"
import { useNavigate } from "react-router-dom";
import WriteArticleForm from "../components/writeArticleForm";

export default function WriteArticle(){   

    // TODO : 이미지 업로드 

    const navigator = useNavigate();
    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");
    const [hashtags, setHashtags] = useState([""]);
    const [isLoading, setIsLoading] = useState(false);

    
    const handleSubmit = async () => {
        // 유저 input 체크
        if (!title){
            alert("제목을 입력해주세요");
            return;
        }
        if (!content){
            alert("본문을 입력해주세요");
            return;
        }
        // 서버 요청
        const endPoint = '/api/article';
        const data = {title, content, hashtags:[...new Set(hashtags)]};     // 해시태그는 중복 제거해서 보냄
        console.table(data);
        setIsLoading(true);
        await axios
            .post(endPoint, data)
            .then((res)=>{
                navigator("/article");
            })      
            .catch((err)=>{
                alert("에러발생");
                console.log(err);
            })
            .finally(()=>{
                setIsLoading(false);
            })
    }

    return(
        <div>
            <h1>작성</h1>
            <WriteArticleForm title={title} setTitle={setTitle} content={content} setContent={setContent} hashtags={hashtags} setHashtags={setHashtags}/>
            <button disabled={isLoading} onClick={handleSubmit}>제출</button>
        </div>
    )
}
