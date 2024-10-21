import axios from "axios";
import {randomUUID} from "node:crypto";

describe("When doing basic requests against api", () => {
    const createCustomer = async (): Promise<any> =>  {
        let response = await axios.post("http://localhost:8080/customers", {
            firstName: 'John',
            lastName: 'Smith',
            city: 'Los Angeles',
            country: 'USA',
            email: `${randomUUID()}@gmailer.com`
        })

        expect(response.status).toBe(201)
        return response.data
    }

    const createCar = async (): Promise<any> =>  {
        const customer = await createCustomer()
        let response = await axios.post(`http://localhost:8080/customers/${customer.id}/cars`, {
            "model": "Jetta",
            "make": "VW",
            "producedIn": "2018"
        })

        expect(response.status).toBe(201)
        return {
            customerId: customer.id,
            car: response.data
        }
    }

    it("GET /customers/{customerId} should succeed", async () => {
        let response = await axios.get("http://localhost:8080/customers/1");
        expect(response.status).toBe(200)
        expect(response.data).toStrictEqual(    {
                id: 1,
                firstName: 'John',
                lastName: 'Smith',
                city: 'Los Angeles',
                country: 'USA',
                email: 'john@gmail.com'
            }
        )
    })

    it("POST /customers should succeed", async() => {
        const customer = await createCustomer()

        expect(customer).toStrictEqual(    {
                id: customer.id,
                firstName: 'John',
                lastName: 'Smith',
                city: 'Los Angeles',
                country: 'USA',
                email: customer.email
            }
        )
    })

    it("DELETE /customers/{customerId} should succeed", async() => {
        const customer = await createCustomer()

        let response = await axios.delete(`http://localhost:8080/customers/${customer.id}`);
        expect(response.status).toBe(204)
    })

    it("PATCH /customers/{customerId} should succeed", async() => {
        const customer = await createCustomer()

        let response = await axios.patch(`http://localhost:8080/customers/${customer.id}`, {
            id: customer.id,
            firstName: "Jim",
            lastName: customer.lastName,
            city: customer.city,
            country: customer.country,
            email: customer.email
        });
        expect(response.status).toBe(200)
        expect(response.data.firstName).toBe("Jim")
    })

    it("GET /customers/{customerId}/cars/{carId} should succeed", async () => {
        let response = await axios.get("http://localhost:8080/customers/1/cars/5d37c2cc-45dd-40a3-8c0e-1fb5dd92f131");
        expect(response.status).toBe(200)
        expect(response.data).toStrictEqual(        {
                id: '5d37c2cc-45dd-40a3-8c0e-1fb5dd92f131',
                customerId: 1,
                model: 'Jetta',
                make: 'VW',
                producedIn: 2020
            }
        )
    })

    it("POST /customers/{customerId}/cars should succeed", async () => {
        await createCar()
    })

    it("DELETE /customers/{customerId}/cars/{carId} should succeed", async () => {
        const {customerId, car} = await createCar()
        let response = await axios.delete(`http://localhost:8080/customers/${customerId}/cars/${car.id}`);
        expect(response.status).toBe(204)
    })

    it("PATCH /customers/{customerId}/cars/{carId} should succeed", async () => {
        const {customerId, car} = await createCar()
        let response = await axios.patch(`http://localhost:8080/customers/${customerId}/cars/${car.id}`,{
            model: 'Jetta',
            make: 'VW',
            producedIn: 2021
        });
        expect(response.status).toBe(200)
        expect(response.data.producedIn).toBe(2021)
    })
})