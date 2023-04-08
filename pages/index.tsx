import Head from 'next/head'
import styles from 'styles/Home.module.css'
import { useEffect, useState } from 'react'
import Products from './products'

export default function Home() {
  const [products, setProducts] = useState<
    { id: string; properties: { id: string }[] }[]
  >([])

  useEffect(() => {
    fetch('/api/get-items')
      .then((res) => res.json())
      .then((data) => setProducts(data.items))
      .catch(console.error)
  }, [])

  const handleGetDetail = (pageId: string, propertyId: string) => {
    fetch(`/api/get-detail?pageId=${pageId}&propertyId=${propertyId}`)
      .then((res) => res.json())
      .then((data) => alert(data.detail))
      .catch(console.error)
  }

  return (
    <>
      <Head>
        <title>Commerce</title>
        <meta name="description" content="Commerce" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="icons" href="/favicon.ico" />
      </Head>

      <main className={styles.main}></main>
      <Products />
    </>
  )
}
