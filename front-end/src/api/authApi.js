import axios from "axios";

export async function regsterApi(data, successCallback, failureCallback){
    const endPoint = '/api/user/register';
    await axios.post(endPoint, data)
        .then(successCallback)
        .catch(failureCallback);
}