import Head from 'next/head'
import { useEffect, useState } from 'react'
import MyCarousel from './carousel'
import EditProduct from './products/[id]/edit'

export default function Home() {
  const [products, setProducts] = useState([])

  useEffect(() => {
    fetch('/api/get-products')
      .then((res) => res.json())
      .then((data) => setProducts(data.items))
      .catch(console.error)
  }, [])

  return (
    <>
      <Head>
        <title>Commerce</title>
        <meta name="description" content="Commerce" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="icons" href="/favicon.ico" />
      </Head>
      <MyCarousel/>
      <EditProduct/>
    </>
  )
}
