export class OrderEvent {

    public  orderId:String;
    public  message:String;
    public  status:String;
    public  stationName:String;
    public  capacity:String;
    public  type:String;
    public  scheduledDate:Date;

    constructor(orderId:String, message:String,status:String, stationName:String,capacity:String,type:String,scheduledDate:Date){
        this.orderId=orderId;
        this.message=message;
        this.status=status;
        this.stationName=stationName;
        this.capacity=capacity;
        this.type=type;
        this.scheduledDate=scheduledDate;
    }

}