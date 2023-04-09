import ProductModel from 'model/ProductModel'
import { useEffect, useState } from 'react'
import { css } from '@emotion/css'
import Image from 'next/image'
import MyEditor from '../../components/Editor'
import { Pagination, SegmentedControl } from '@mantine/core'
import { EditorState, convertFromRaw } from 'draft-js'

export default function Products() {
  /**
   * currentPage : 현재 페이지수
   * currentCategory : 현재 선택한 카테고리(default : ALL)
   * categories : 카테고리 종류
   * totoalPage : 전체 페이지수
   * products :
   */
  const [currentPage, setCurrentPage] = useState<number>(1)
  const [currentCategory, setCurrentCategory] = useState<string>('ALL')
  const [categories, setCategories] = useState<
    { label: String; value: String }[]
  >([])
  const [totoalPage, setTotalPage] = useState<number>(0)
  const [products, setProducts] = useState<ProductModel[]>([])

  useEffect(() => {
    fetch(
      `/api/get-products?page=${currentPage - 1}&category=${currentCategory}`
    )
      .then((res) => res.json())
      .then((data) => {
        setProducts([...data.items.content])
        setTotalPage(data.items.totalPages)
      })
      .catch(console.error)
  }, [currentPage, currentCategory])

  useEffect(() => {
    fetch('/api/get-category')
      .then((res) => res.json())
      .then((data) =>
        setCategories([{ label: '전체', value: 'ALL' }, ...data.items])
      )
      .catch(console.error)
  }, [])

  return (
    <>
      <h1
        className={css`
          margin-bottom: 20px;
        `}
      >
        Products
      </h1>

      <div
        className={css`
          margin-bottom: 20px;
        `}
      >
        {
          <SegmentedControl
            value={currentCategory}
            onChange={setCurrentCategory}
            data={[
              ...categories.map((cat) => ({
                label: String(cat.label),
                value: String(cat.value),
              })),
            ]}
            color="dark"
          />
        }
      </div>

      <div>
        {products &&
          products.map((prod) => {
            const editorState = EditorState.createWithContent(
              convertFromRaw(JSON.parse(prod.description))
            )

            return (
              <div
                className={css`
                  padding: 5px;
                  display: grid;
                  grid-template-columns: repeat(2, 1fr);
                  grid-gap: 5px;
                `}
                key={prod.id}
              >
                <div>
                  {prod.imgUrl && (
                    <Image
                      width={300}
                      height={200}
                      src={prod.imgUrl}
                      alt={prod.name}
                      // TODO : add blur image
                      // placeholder="blur"
                      // blurDataURL=""
                      className={css`
                        border-radius: 10%;
                      `}
                    />
                  )}
                </div>

                <div
                  className={css`
                    padding: 5px;
                    display: grid;
                    grid-template-rows: 2fr 1fr 2fr;
                    grid-gap: 5px;
                  `}
                >
                  <div>
                    <p
                      className={css`
                        font-weight: 800;
                      `}
                    >
                      {prod.name}
                    </p>
                  </div>

                  <div
                    className={css`
                      display: flex;
                      justify-content: space-between;
                      width: 300px;
                      margin-top: 5px;
                    `}
                  >
                    <span>\{prod.price?.toLocaleString('ko-KR')}</span>
                    <span
                      className={css`
                        font-style: italic;
                        color: gray;
                      `}
                    >
                      {prod.category}
                    </span>
                  </div>

                  <MyEditor editorState={editorState} readOnly />
                </div>

                <br />
              </div>
            )
          })}
      </div>

      <div
        className={css`
          width: 100%;
          display: flex;
          margin-top: 10px;
          justify-content: center;
        `}
      >
        <Pagination
          value={currentPage}
          onChange={setCurrentPage}
          total={totoalPage}
          className={css`
            margin: 'auto';
          `}
        />
        ;
      </div>
    </>
  )
}
