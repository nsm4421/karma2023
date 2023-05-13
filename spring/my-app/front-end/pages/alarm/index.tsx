import { Alarm } from "@/utils/model";
import { useEffect, useState } from "react";

export default function Alarm() {
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [isError, setIsError] = useState<boolean>(false);
  const [page, setPage] = useState<number>(0);
  const [totalPages, setTotalPages] = useState<number>(1);
  const [alarms, setAlarms] = useState<Alarm[]>([]);

  const getAlarms = async () => {
    const token = localStorage.getItem("token");
    if (!token) {
      alert("Need to login");
      return;
    }
    try{
        const data = await fetch(`/api/alarm/get?page=${page}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
          .then((res) => res.json())
          .then((json) => json.data)
          .catch((err) => console.error(err));
          setAlarms(data.content);
          setTotalPages(data.totalPages);   
          setIsError(false);
    } catch(e){
        setIsError(true);
    } finally{
        setIsLoading(false);
    }
  };

  const sse = () =>{
    const token = localStorage.getItem("token");
    if (!token) {
      alert("Need to login");
      return;
    }
    const endPoint = `http://localhost:8080/api/alarm/subscribe?token=${token}`;
    const eventSorce = new EventSource(endPoint);
    eventSorce.addEventListener("open", (e)=>{
      console.log("connection opened...");
    })
    eventSorce.addEventListener("ALARM", (e)=>{
      console.log("alarm event occured ..." + e);
      console.log(JSON.parse(e.data));
    })
    eventSorce.addEventListener("error", (e)=>{
      console.log(e);
      eventSorce.close();
    })
  }

  useEffect(() => {
    setIsLoading(true);
    getAlarms();
    sse()
    setIsLoading(false);
  }, []);

  return (
    <>
      <h1>Alarm</h1>
      {
        alarms.map((a, i)=>(<div>{a.id}</div>))
      }
    </>
  );
}
