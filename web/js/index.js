function deleteFruit(id) {
   if (confirm("是否确认删除?")){
       window.location.href='fruit?fid='+id+'&operate=del';
   }
}
function page(pageNo){
    window.location.href="fruit?pageNo="+pageNo;
}