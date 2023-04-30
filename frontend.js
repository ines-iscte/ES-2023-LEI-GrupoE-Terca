const jsonFilePath = "arquivo.json";

const fs = require('fs');
let jsonString = fs.readFileSync(jsonFilePath);
let jsonObject = JSON.parse(jsonString);
let events = rootNode['aulas'];
const jsonArray = [jsonObject];

const exportDataFromJson = (jsonArray) => {
    if (Array.isArray(events)) {
        events.forEach(function(event) {
            // Check if 'event' is not null
            if (event != null) {
                let title = event['Unidade Curricular'];
                let description = event['Turno'] + ' - ' + event['Turma'];
                let location = event['Sala atribuida a aula'] || ''; // Set the location to an empty string if it's null
                let startDateTimeString = event['Data da aula'] + ' ' + event['Hora inicio da aula'];
                let endDateTimeString = event['Data da aula'] + ' ' + event['Hora fim da aula'];

                // Convert the start and end date/time strings into a JavaScript Date objects
                let startDateTime = new Date(startDateTimeString);
                let endDateTime = new Date(endDateTimeString);

                // Create a new entry object and add it to the calendar
                let entry = {
                    title: title,
                    description: description,
                    location: location,
                    startDateTime: startDateTime,
                    endDateTime: endDateTime
                };
                calendar.addEntry(entry);
                print(entry);
            }
        });

    } else {

    }
    /*const worksheet = XLSX.utils.json_to_sheet(jsonArray);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, 'Sheet1');
    const excelBuffer = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });
    const data = new Uint8Array(excelBuffer);
    return data;

     */
}
