import Link from 'next/link'

export default function Home() {
  return (
    <main>
      <h1>Home</h1>
      <Link href={"/auth/sign-up"}>Go To Sign Up Page</Link>
    </main>
  )
}
