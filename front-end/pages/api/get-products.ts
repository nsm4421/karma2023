import type { NextApiRequest, NextApiResponse } from 'next'
import axios from 'axios'
import ProductModel from 'model/ProductModel'

type Data = {
  message:String,
  items : {
      content : ProductModel[],
      pageable : any,
      totalElements : Number,
      totalPages : Number
  }[] 
}

async function getProducts(page:Number, category:String) {
  try {
    const endPoint = 'http://localhost:8080/api/product?page=' + (page?`${page}`:'0') + (category=='ALL'?'':`&category=${category}`)
    const res = await axios.get(endPoint)
    return res.data;
  } catch (e) {
    console.error(JSON.stringify(e))
  }
}

export default async function handler(
  req: NextApiRequest,
  res: NextApiResponse<Data>
) {
  const { page, category } = req.query
  try {
    const items = await getProducts(Number(page), String(category))
    res.status(200).json({ message: 'Get item success', items: items })
  } catch (e) {     
    console.error(e)
    return res.status(400).json({ message: 'Fail to get item', items:[]})
  }
}
