import axios from "axios";

export async function regsterApi(username, nickname, email, password, description, successCallback, failureCallback){
    const endPoint = '/api/user/register';
    const data = {username, nickname, email, password, description};
    await axios.post(endPoint, data)
        .then(successCallback)
        .catch(failureCallback);
}