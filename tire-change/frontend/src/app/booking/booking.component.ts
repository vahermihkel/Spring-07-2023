import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { TimeService } from "../services/time.service";

@Component({
  selector: "app-booking",
  templateUrl: "./booking.component.html",
  styleUrls: ["./booking.component.scss"],
})
export class BookingComponent implements OnInit {
  days: { day: number; isClickable: boolean }[] = [];
  dateTime: Date = new Date();
  selectedMonthNumber: number = new Date().getMonth();
  selectedYear: number = new Date().getFullYear();
  months = [
    "jan",
    "feb",
    "mar",
    "apr",
    "may",
    "jun",
    "jul",
    "aug",
    "sep",
    "oct",
    "nov",
    "dec",
  ];
  selectedMonthString: string = this.months[this.selectedMonthNumber];
  tireChangeTimeSlots: any[] = [];
  calendarTimeSlots: { day: number; timeSlots: any[] }[] = [];
  selectedDay = -1;
  dayTimeSlots: any[] = [];
  upperTimeSlots: any[] = [];
  middleTimeSlots: any[] = [];
  lowerTimeSlots: any[] = [];
  hoursShown = {
    minHour: 0,
    maxHour: 0,
    middleLeftHour: 0,
    middleRightHour: 0,
  };
  unselected: number = 0;
  timeSlots: Set<any> = new Set<any>();
  showTimeSlots: boolean = false;
  showCalendar: boolean = false;
  previousArrowDisabled: boolean = false;

  constructor(
    private router: Router,
    private timeService: TimeService
  ) {}

  ngOnInit(): void {
    this.getDaysInMonth(this.selectedYear, this.selectedMonthNumber);
    this.checkIfPreviousArrowDisabled();

    this.timeService.getTimes().subscribe(res => {
      this.tireChangeTimeSlots = res;
      this.getCalendarTimeSlots();
    })
    
    setTimeout(() => {
      this.showCalendar = true;
    }, 200);
  }

  isAlreadySelected(timeSlotId: string): boolean {
    let isSelected = false;
    this.timeSlots.forEach((chosen) => {
      if (timeSlotId === chosen.id) {
        isSelected = true;
      }
    });
    return isSelected;
  }

  onTimeSlotClick(timeSlot: any) {
    
  }

  reserveTimeSlot(timeSlot: any) {
    
  }

  onChangeMonth(change: number) {
    this.selectedDay = -1;
    this.upperTimeSlots = [];
    this.middleTimeSlots = [];
    this.lowerTimeSlots = [];
    this.selectedMonthNumber += change;
    if (this.selectedMonthNumber === -1) {
      this.selectedYear -= 1;
      this.selectedMonthNumber = 11;
    } else if (this.selectedMonthNumber === 12) {
      this.selectedYear += 1;
      this.selectedMonthNumber = 0;
    }
    this.checkIfPreviousArrowDisabled();
    this.selectedMonthString = this.months[this.selectedMonthNumber];
    this.getTimeSlotsFromBron();
    this.getDaysInMonth(this.selectedYear, this.selectedMonthNumber);
  }

  getTimeSlotsFromBron() {
    
  }

  getSingleDayTimeSlotsFromBron(date: Date) {
    
    
  }

  getDaysInMonth(year: number, month: number) {
    let date = new Date(year, month, 1);
    this.dateTime = date;
    this.days = [];
    let firstDayOfMonth = date.getDay();
    if (firstDayOfMonth == 0) {
      firstDayOfMonth = 7;
    }
    for (let index = 0; index < firstDayOfMonth; index++) {
      this.days.push({ day: 0, isClickable: false });
    }
    let daysInMonth = 0;
    while (date.getMonth() === month) {
      this.days.push({ day: new Date(date).getDate(), isClickable: false });
      date.setDate(date.getDate() + 1);
      daysInMonth++;
    }
    return daysInMonth;
  }

  getCalendarTimeSlots() {
    this.calendarTimeSlots = this.groupByArray(
      this.tireChangeTimeSlots,
      "time"
    );
  }

  groupByArray(timeslots: any[], day: any) {
    console.log(timeslots);
    return timeslots.reduce((rv, x) => {
      console.log(rv);
      console.log(x);
      console.log(day);
      let v = day instanceof Function ? day(x) : x[day];
      let d = Number(v.substring(8, 10));
      let el = rv.find((r: any) => r && r.time === d);
      if (el) {
        el.timeSlots.push(x);
      } else {
        let index = this.days.findIndex((day) => day.day == d);
        if (index != -1) {
          this.days[index].isClickable = true;
        }
        rv.push({ day: d, timeSlots: [x] });
      }
      return rv;
    }, []);
  }

  checkIfClickable(day: number) {
    return this.calendarTimeSlots.some((e) => e.day === day);
  }

  checkIfPreviousArrowDisabled() {
    const currentDate = new Date();
    if (
      this.selectedYear == currentDate.getFullYear() &&
      this.selectedMonthNumber == currentDate.getMonth()
    ) {
      this.previousArrowDisabled = true;
    } else {
      this.previousArrowDisabled = false;
    }
  }

  onTimeSelected() {
    this.router.navigateByUrl("book");
  }

  onDayClick(dayNumber: number) {
    this.showTimeSlots = false;
    this.selectedDay = dayNumber;
    let date: Date = new Date(
      this.selectedYear,
      this.selectedMonthNumber,
      dayNumber + 1
    );
    this.hoursShown = {
      minHour: 0,
      maxHour: 0,
      middleLeftHour: 0,
      middleRightHour: 0,
    };
    this.showTimeSlots = true;


    let min = new Date(this.dayTimeSlots[0].start);
    let max = new Date(this.dayTimeSlots[0].start);

    this.dayTimeSlots.forEach((timeSlot) => {
      let date = new Date(timeSlot.start);
      if (date < min) {
        min = date;
      }
      if (date > max) {
        max = date;
      }
    });
    this.hoursShown.minHour = min.getHours();
    this.hoursShown.maxHour = max.getHours() + 1;
    console.log(this.hoursShown);
    let average = (this.hoursShown.maxHour - this.hoursShown.minHour) / 3;

    if (average < 1) {
      this.hoursShown.middleLeftHour = this.hoursShown.minHour + 1;
      this.hoursShown.middleRightHour = this.hoursShown.minHour + 2;
      this.hoursShown.maxHour = this.hoursShown.minHour + 3;
    } else {
      this.hoursShown.middleLeftHour = Math.ceil(
        this.hoursShown.minHour + average
      );
      this.hoursShown.middleRightHour = Math.ceil(
        this.hoursShown.minHour + average * 2
      );
    }
    this.upperTimeSlots = [];
    this.middleTimeSlots = [];
    this.lowerTimeSlots = [];
    this.dayTimeSlots.forEach((item) => {
      let start = new Date(item.start);
      let hour = start.getHours();
      if (
        hour >= this.hoursShown.minHour &&
        hour < this.hoursShown.middleLeftHour
      ) {
        this.pushAsUnique(this.upperTimeSlots, start, item.id);
      } else if (
        hour >= this.hoursShown.middleLeftHour &&
        hour < this.hoursShown.middleRightHour
      ) {
        this.pushAsUnique(this.middleTimeSlots, start, item.id);
      } else if (
        hour >= this.hoursShown.middleRightHour &&
        hour <= this.hoursShown.maxHour
      ) {
        this.pushAsUnique(this.lowerTimeSlots, start, item.id);
      } else {
        console.log("liiga varajane või hiline tund!");
      }
    });
  }

  pushAsUnique(timeSlots: any[], date: Date, id: string) {
    let index = timeSlots.findIndex(
      (time) => new Date(time.time).getTime() === date.getTime()
    );
    if (index === -1) {
      timeSlots.push({ date: date, id: id });
    }
    timeSlots.sort(
      (a, b) => new Date(a.time).getTime() - new Date(b.time).getTime()
    );
  }

  
}
