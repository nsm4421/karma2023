import { CredentialResponse, GoogleLogin } from '@react-oauth/google'

export default function MyGoogleLogin() {
  const handleSuccess = (credentialResponse: CredentialResponse): void => {
    // const endPoint = `/api/auth/get-token?credentail=${credentialResponse.credential}`
    // fetch(endPoint)
    // .then(res=>res.json())
    // .then(data=>{console.log(data)})
  }
  const handleError = () => {
    console.error("error")
  }

  return <GoogleLogin onSuccess={handleSuccess} onError={handleError}></GoogleLogin>
}
