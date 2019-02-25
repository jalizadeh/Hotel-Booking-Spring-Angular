import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit{
  //title = 'angular2';
    
  public submitted:boolean;
  roomsearch : FormGroup;
  rooms : Room[];
    
  ngOnInit(){
    this.roomsearch = new FormGroup({
        checkin: new FormControl(''),
        checkout: new FormControl('')
    });
      
    this.rooms = ROOMS;
  }
    
  onSubmit({value, valid} : {value:RoomSearch, valid:boolean}){
    console.log(value);
  }
    
  reserveRoom(value:string){
    console.log("Room id for reservation: " + value);
  }
}

export interface RoomSearch{
  checkin : string;
  checkout : string;
}

export interface Room{
  id : string;
  roomNumber : string;
  price : string;
  links : string;
}

var ROOMS : Room[] = [
    {
        "id" : "38932123",
        "roomNumber" : "409",
        "price" : "20",
        "links" : ""
    },
    {
        "id" : "765897",
        "roomNumber" : "410",
        "price" : "25",
        "links" : ""
    },
    {
        "id" : "8709212",
        "roomNumber" : "411",
        "price" : "28",
        "links" : ""
    }
];