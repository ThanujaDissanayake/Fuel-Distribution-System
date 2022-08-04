export class ScheduledOrder{
        public orderId:String;
        public stationName:String;
        public type:String;
        public capacity:String;
        public status:String;
        public scheduedDate:Date
    
        constructor(orderId:String,stationName:String,type:String,capacity:String,status:String,scheduedDate:Date){
            this.orderId=orderId
            this.stationName=stationName;
            this.type=type;
            this.capacity=capacity;
            this.status=status;
            this.scheduedDate=scheduedDate;
        }
}