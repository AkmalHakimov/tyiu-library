import React, {useEffect, useState} from 'react'
import { Radio, Select, Space,Table } from 'antd';
import ApiRequest from '../../../utils/ApiRequest';

export default function TableAdabiyotlar() {

    const [data,setData] = useState([])
    const options = [
        {label: "12", value: "qalay"},
        {label: "aaa", value: "a1"},
    ]

    const columns =[
        {
            title: '#',
            dataIndex: 'id',
            sorter: true,
            render: (name) =>`${name}`,
            width: '10%',
          },
          {
            title: "Kitob nomi",
            dataIndex: "name",
            with: "30%"
          },
          {
            title: "Kitob muallifi",
            dataIndex: "author",
            with: "30%"
          },
          {
            title: "Kitob sanasi",
            dataIndex: "bookDate",
            render: (bookDate) => new Date(bookDate).getFullYear(),
            with: "30%"
          }
    ]

    useEffect(()=>{
        getBooks()
    },[])

    function getBooks(){
        ApiRequest("/book","get").then(res=>{
            setData(res.data.content)
        })
    }

    function handleChange(value){
        console.log(value);
    }
  return (
    <div>
        <Select
          defaultValue="a1"
          onChange={handleChange}
          style={{
            width: 300,
          }}
          options={options}
        />
        <Table columns={columns} dataSource={data}/>
    </div>
  )
}
