import { Client } from '@notionhq/client'
import type { NextApiRequest, NextApiResponse } from 'next'
import { notionSecret, notionDbId } from '../../notionConfig'

// API response
type Data = {
  message: string
}

// notion client
const notion = new Client({
  auth: notionSecret,
})

// add name in database
async function notionApiTest(name: string) {
  try {
    const res = await notion.pages.create({
      parent: {
        database_id: notionDbId,
      },
      properties: {
        title: [
          {
            text: {
              content: name,
            },
          },
        ],
      },
    })
    console.log('response >', res)
  } catch (e) {
    console.error(JSON.stringify(e))
  }
}

export default async function handler(
  req: NextApiRequest,
  res: NextApiResponse<Data>
) {
  const { name } = req.query

  if (name == null) {
    return res.status(400).json({ message: 'name is null...' })
  }

  try {
    await notionApiTest(String(name))
  } catch (e) {
    return res.status(400).json({ message: `Fail to save ${name}` })
  }

  return res.status(200).json({ message: `Success add ${name}` })
}
