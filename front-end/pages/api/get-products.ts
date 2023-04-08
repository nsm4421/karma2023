import type { NextApiRequest, NextApiResponse } from 'next'
import axios from 'axios'

export type Data = {
    items?: {
      id:Number,
      name:String,
      imgUrl:String,
      categoryId:String,
      description:String,
      price:Number
    }[]
    message: string
}

async function getProducts() {
  try {
    const endPoint = "http://localhost:8080/api/product"
    const res = await axios.get(endPoint)
    console.debug(res.data.content??res);
    return res.data.content;
  } catch (e) {
    console.error(JSON.stringify(e))
  }
}

export default async function handler(
  req: NextApiRequest,
  res: NextApiResponse<Data>
) {
  try {
    const items = await getProducts()
    res.status(200).json({ message: 'Get item success', items: items })
  } catch (e) {     
    console.error(e)
    return res.status(400).json({ message: 'Fail to get item' })
  }
}
