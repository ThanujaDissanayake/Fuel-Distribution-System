export class Order{
    public stationName:String;
    public type:String;
    public capacity:Number;

    constructor(stationName:String,type:String,capacity:Number){
        this.stationName=stationName;
        this.type=type;
        this.capacity=capacity;
    }

}