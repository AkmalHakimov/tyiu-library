import React, {useEffect, useState} from 'react'
import { Radio, Select, Space,Table,Button, Input,Pagination } from 'antd';
import ApiRequest from '../../../utils/ApiRequest';

export default function TableAdabiyotlar() {

    const [data,setData] = useState([])
    const [totalPages,setTotalPages] = useState("");
    const [loading,setLoading] = useState(false)
    const [searchInp,setSearchInp] = useState("")
    const [selectVal,setSelectVal] = useState(0)
    const [page,setPage] = useState(1)

    const [options,setOptions] = useState([
      {label: "Yo'nalishlar", value: ""}
    ])

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
        getBooks(page)  
        getCategories()
    },[])

    function getCategories(){
      ApiRequest("/category", "get").then(res=>{
        res.data.content.map((item,index)=>{
            options.push({
              label: item.name,
              value:item.id,
              key: index  
            })
            setOptions([...options])
        })
      })
    }
    function getBooks(page,search = searchInp,select = selectVal){
      setLoading(true)
        ApiRequest(`/book?page=${page}&categoryId=${select}&search=` + (search),"get").then(res=>{
            setTotalPages(res.data.totalElements)
            setData(res.data.content)
            setLoading(false)
        })
    }

    const onShowSizeChange = (current, pageSize) => {
      console.log(current, pageSize);
    };
  return (
    <div>
        <div className='mb-4 d-flex align-items-center justify-content-between'>
        <div style={{width:450}} className='d-flex align-items-center justify-content-between'>
        <Button type='primary'>Add a row</Button>
        <Select
          defaultValue="Yo'nalishlar"
          onChange={(val)=>{setSelectVal(val); getBooks(page,searchInp,val)}}
          style={{
            width: 300,
          }}
          options={options}
        />
        </div>
        <div><Input value={searchInp} onChange={(e)=>{setSearchInp(e.target.value); getBooks(page, e.target.value)}} placeholder='search...'/></div>
        </div>
        <Table pagination={{
          pageSize: 6,
          total:totalPages,
          onChange: (page)=>{
            getBooks(page)
          },
        }}   loading={loading} columns={columns} dataSource={data}/>
    </div>
  )
}
