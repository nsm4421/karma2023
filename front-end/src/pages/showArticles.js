import axios from "axios"
import { useEffect, useState } from "react";
import { getArticlesApi } from "../api/articleApi";
import Article from "../components/article";

export default function ShowArticle(){

    const [isLoading, setIsLoading] = useState(false);
    const [articles, setArticles] = useState([]);
    const [pagebale, setPageable] = useState({});
    const [totalPages, setTotalPages] = useState(1);
    const [totalElements, setTotalElements] = useState(1);

    const successCallbackForGetArticlesApi = (res) => {
        const data = res.data.data;
        setArticles([...data.content]);
        setPageable(data.pagebale);
        setTotalPages(data.totalPages);
        setTotalElements(data.totalElements);
    }

    const failureCallback = console.log;

    useEffect(()=>{
        const getArticle = async ()=> {
            setIsLoading(true);
            await getArticlesApi(successCallbackForGetArticlesApi, failureCallback);
            setIsLoading(false);
        }
        getArticle();
    }, [])

    if (isLoading){
        return (
            <div>
                <h1>로딩중...</h1>
            </div>
        )
    }

    return (
        <div>
            <h1>Show Article</h1>
            {
                articles.map((a, i)=>{
                    return (
                        <Article key={i} article={a}/>
                    )
                })
            }

            <p>전체 게시글수 : {totalElements}</p>
            <p>총 페이지수 : {totalPages}</p>
        </div>
    )
}