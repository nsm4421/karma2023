import { Client } from '@notionhq/client'
import type { NextApiRequest, NextApiResponse } from 'next'
import { notionSecret, notionDbId } from '../../notionConfig'
import { get } from 'http'

// API response
type Data = {
  items?: any
  message: string
}

// notion client
const notion = new Client({
  auth: notionSecret,
})

async function getItems() {
  try {
    const res = await notion.databases.query({
      database_id: notionDbId,
      sorts: [
        {
          property: 'price',
          direction: 'ascending',
        },
      ],
    })
    console.debug(res)
    return res
  } catch (e) {
    console.error(JSON.stringify(e))
  }
}

export default async function handler(
  req: NextApiRequest,
  res: NextApiResponse<Data>
) {
  try {
    const items = await getItems()
    res.status(200).json({ message: 'Get item success', items: items?.results })
  } catch (e) {
    console.error(e)
    return res.status(400).json({ message: 'Fail to get item' })
  }
}
