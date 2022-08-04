export class Order{
    public stationName:String;
    public type:String;
    public capacity:String;

    constructor(stationName:String,type:String,capacity:String){
        this.stationName=stationName;
        this.type=type;
        this.capacity=capacity;
    }

}