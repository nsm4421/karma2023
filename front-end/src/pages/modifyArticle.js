import axios from "axios";
import { useEffect, useState } from "react"
import { useNavigate, useParams } from "react-router-dom";
import WriteArticleForm from "../components/writeArticleForm";

export default function ModifyArticle(){   
    
    const params = useParams();
    const navigator = useNavigate();
    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");
    const [hashtags, setHashtags] = useState([""]);
    const [isLoading, setIsLoading] = useState(false); 

    useEffect(()=>{        
        setIsLoading(true);
        const endPoint = `/api/article/search/${params.id}`;
        axios.get(endPoint).then((res)=>{
            return res.data.data;
        }).then((data)=>{
            setTitle(data.title);
            setContent(data.content);    
            setHashtags(data.hashtags);
        }).catch((err)=>{
            console.log(err);
        })
        setIsLoading(false);
    }, []);

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
        const data = {articleId:params.id, title, content, hashtags:[...new Set(hashtags)]};     // 해시태그는 중복 제거해서 보냄
        setIsLoading(true);
        await axios
            .put(endPoint, data)
            .then((res)=>{
                return res.data;
            })
            .then((data)=>{
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
            <h1>수정</h1>
            <WriteArticleForm title={title} setTitle={setTitle} content={content} setContent={setContent} hashtags={hashtags} setHashtags={setHashtags}/>
            <button disabled={isLoading} onClick={handleSubmit}>제출</button>
        </div>
    )
}
