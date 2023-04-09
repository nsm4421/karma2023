import ProductModel from "model/ProductModel";
import { useEffect, useState } from "react";
import { css } from '@emotion/css'
import Image from "next/image";
import MyEditor from "../../components/Editor";
import { Pagination } from '@mantine/core';
import { EditorState, convertFromRaw } from 'draft-js'

export default function Products(){
    const [currentPage, setCurrentPage] = useState<number>(1);
    const [totoalPage, setTotalPage] = useState<number>(0)
    const [products, setProducts] = useState<ProductModel[]>([])
    useEffect(()=>{
        fetch(`/api/get-products?page=${currentPage-1}`)
        .then(res=>res.json())
        .then((data)=>{
            setProducts([...data.items.content])
            setTotalPage(data.items.totalPages)
        })
        .catch(console.error)
    }, [currentPage])

    return (
        <>    
        <div  className={css`
            display: grid;
                   grid-template-columns: repeat(2, 1fr);
                    grid-gap : 5px;
                    `}>
              
        {products&&
            products.map(prod=>{

                const editorState = EditorState.createWithContent(convertFromRaw(JSON.parse(prod.description)))
                
                return <div 
                className={css`
                    padding: 5px;
                `}
                key={prod.id} 
                >
                    {
                        prod.imgUrl&&
                        <Image width={300} height={200} src={prod.imgUrl} alt={prod.name}
                        // TODO : add blur image
                        // placeholder="blur"
                        // blurDataURL=""
                        className={css`border-radius:10%;`}
                        />
                    }
                    <p className={css`font-weight:800;`}>{prod.name}</p>
                    <p>\{prod.price?.toLocaleString('ko-KR')}</p>
                    <MyEditor editorState={editorState} readOnly />
                    <br/>
                </div>
            })
        }
        </div> 

        <div className={css`
            width: 100%;
            display: flex;
            margin-top: 10px;
            justify-content:center;
        `}>
            <Pagination value={currentPage} onChange={setCurrentPage} total={totoalPage} 
                className={css`
                margin: 'auto';
                `}/>;

        </div>
        </>
    )

}