import axios from "axios";

export async function writeArticleApi(data, successCallback, failureCallback){
    const endPoint = '/api/article';
    await axios
        .post(endPoint, data)
        .then(successCallback)      
        .catch(failureCallback);
}

export async function modifyArticleApi(data, successCallback, failureCallback){
    const endPoint = '/api/article';
    await axios
        .put(endPoint, data)
        .then(successCallback)      
        .catch(failureCallback);
}

export function getArticleApi(articleId, successCallback, failureCallback){
    const endPoint = `/api/article/search/${articleId}`;
    axios.get(endPoint)
        .then(successCallback)
        .catch(failureCallback)
}

export function getArticlesApi(successCallback, failureCallback){
    const endPoint = "/api/article";
    axios.get(endPoint)
        .then(successCallback)
        .catch(failureCallback);
}

export async function deleteArticleApi(articleId, successCallback, failureCallback) {
    const endPoint = `/api/article/${articleId}`;
    await axios
        .delete(endPoint)
        .then(successCallback)
        .catch(failureCallback);
}