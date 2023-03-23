import { useNavigate } from "react-router-dom";
import { deleteArticleApi } from "../api/articleApi";
import Comment from "./comment";

export default function Article({article, isLoading, setIsLoading}){

    const navigator = useNavigate();

    const moveToModifyPage = () => {
        navigator(`/article/modify/${article.articleId}`);
    }

    // 새로고침하기
    const successCallback = () => {
        window.location.href = '/article';
    };

    const failureCallback = console.log;

    const handleDeleteArticle = async () => {
        setIsLoading(true);
        await deleteArticleApi(article.articleId, successCallback, failureCallback);
        setIsLoading(false);
    }

    return (
        <div>
            <div>
                <label>제목</label>
                <span>{article.title}</span>
            </div>
            <div>
                <label>작성자</label>
                <span>{article.userAccountDto.nickname}</span>
            </div>
            {/* TODO : 내가 쓴 게시물만 아래 버튼들이 보이게 하기 */}
            <div>
                <button disabled={isLoading} onClick={moveToModifyPage}>수정하기</button>
                <button disabled={isLoading} onClick={handleDeleteArticle}>삭제하기</button>
            </div>
            <div>
                <label>본문</label>
                <span>{article.content}</span>
            </div>
            <div>
                <label>해시태그</label>
                {
                    article.hashtags.map((h, i)=>{
                        return(
                            <div key={i}>
                                <span>#{h}</span>
                            </div>
                        )
                    })
                }
            </div>
            <div>
                <label>작성시간</label>
                <span>{article.createdAt}</span>
                <br/>
                <label>수정시간</label>
                <span>{article.modifiedAt}</span>
            </div>
            <Comment articleId={article.articleId}/>
        </div>
    )
}