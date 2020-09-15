

function abc(id)
    {
        
            $.post("/details", id,()=>{
                console.log(id);
            })
                
            
            
          
    }