import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import {Http, Response, Headers, RequestOptions} from '@angular/http';
import {Observable} from 'rxjs/Rx';
//import 'rxjs/add/operator/map';
//import 'rxjs/add/operator/catch';
import { map, catchError } from 'rxjs/operators';
//import { map } from 'rxjs/operators';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
    //title = 'angular2'  
    constructor(private http: Http) { }

    private baseurl: string = 'http://localhost:8080';
    public submitted: boolean;
    roomsearch: FormGroup;
    rooms: Room[];
    currentCheckInValue: string;
    currentChekchOutValue: string;
    request:ReserveRoomRequest;

    ngOnInit() {
        this.roomsearch = new FormGroup({
            checkin: new FormControl(''),
            checkout: new FormControl('')
        });

        //this.rooms = ROOMS;

        const roomsearchValueChanges$ = this.roomsearch.valueChanges;

        roomsearchValueChanges$.subscribe(valChange => {
            this.currentCheckInValue = valChange.checkin;
            this.currentChekchOutValue = valChange.checkout;
            }
        )
    }

    onSubmit({value, valid}: { value: RoomSearch, valid: boolean }) {
        console.log(value);
        this.getAll()
            .subscribe(
            rooms => this.rooms = rooms,
            err => {
                console.log(err);
            }
            );
    }

    reserveRoom(value: string) {
        this.request = new ReserveRoomRequest(value, this.currentCheckInValue, this.currentChekchOutValue);
        this.createReservationBody(this.request);
    }

    getAll(): Observable<Room[]> {
        return this.http.get(this.baseurl +
            '/room/reservation/v1?checkin='+this.currentCheckInValue+'&checkout='+this.currentChekchOutValue)
            .pipe(
            map(this.mapRoom)
            , catchError(error => Observable.of(null))
            );
        //.map(this.mapRoom)  
    }

    mapRoom(response: Response): Room[] {
        return response.json().content;
    }
    
    
    createReservationBody(body : ReserveRoomRequest){
        let bodyString = JSON.stringify(body);
        let headers = new Headers({'Content-Type' : 'application/json'});
        let option = new RequestOptions({headers : headers});
        
        this.http.post(this.baseurl + '/room/reservation/v1', body, option)
            .subscribe(res => console.log(res));
    }
}

export interface RoomSearch {
    checkin: string;
    checkout: string;
}

export interface Room {
    id: string;
    roomNumber: string;
    price: string;
    links: string;
}

/*
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
*/

export class ReserveRoomRequest{
    roomId : string;
    checkin : string;
    checkout : string;

    constructor(roomId : string, checkin : string, checkout : string){
        this.roomId = roomId;
        this.checkin = checkin;
        this.checkout = checkout;
    }
}