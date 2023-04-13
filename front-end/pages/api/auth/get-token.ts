import type { NextApiRequest, NextApiResponse } from 'next'
import axios from 'axios'
import jwtDecode from 'jwt-decode'

type Data = {
  message : String,
}

async function getToken(credential:string) {

    const decoded = jwtDecode(credential)

    try {

    } catch (err) {

    }

}

export default async function handler(
  req: NextApiRequest,
  res: NextApiResponse<Data>
) {
    const {credential} = req.query

  try {
    const items = await getToken(String(credential))
    res.status(200).json({ message : 'Google login success' })
  } catch (e) {     
    console.error(e)
    return res.status(400).json({ message: 'Fail to login'})
  }
}
