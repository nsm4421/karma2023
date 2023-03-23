import { useEffect, useState } from "react"
import { useNavigate, useParams } from "react-router-dom";
import { getArticleApi, modifyArticleApi } from "../api/articleApi";
import WriteArticleForm from "../components/writeArticleForm";

export default function ModifyArticle(){   
    
    const params = useParams();
    const navigator = useNavigate();
    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");
    const [hashtags, setHashtags] = useState([""]);
    const [isLoading, setIsLoading] = useState(false); 

    const checkInput = () => {
        if (!title){
            alert("제목을 입력해주세요");
            return;
        }
        if (!content){
            alert("본문을 입력해주세요");
            return;
        }
    }

    const getArticle = async () => {
        const successCallback = (res) => {
            const data = res.data.data;
            setTitle(data.title);
            setContent(data.content);
            setHashtags(data.hashtags);
        }
        const failureCallback = console.log;
        await getArticleApi(params.id, successCallback, failureCallback);
    }

    const handleSubmit = async () => {
        const successCallback = () => {
            navigator("/article");
        }
        const failureCallback = console.log;
        await checkInput();
        setIsLoading(true);
        await modifyArticleApi(params.id, title, content, hashtags, successCallback, failureCallback);
        setIsLoading(false);
    }

    useEffect(()=>{       
        const _getArticle = async () => {
            setIsLoading(true);
            await getArticle();
            setIsLoading(false);
        }
        _getArticle();
    }, []);

    return(
        <div>
            <h1>수정</h1>
            <WriteArticleForm title={title} setTitle={setTitle} content={content} setContent={setContent} hashtags={hashtags} setHashtags={setHashtags}/>
            <button disabled={isLoading} onClick={handleSubmit}>제출</button>
        </div>
    )
}
