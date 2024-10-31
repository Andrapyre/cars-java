import axios from "axios"
import { randomUUID } from "node:crypto"

const getAuthHeadersForCustomer = (customer: any) => {
    const { apiKey } = customer
    const bearerToken = `Bearer ${Buffer.from(apiKey).toString("base64")}`
    return {
        headers: {
            Authorization: bearerToken,
        },
    }
}

const hostWithCustomers = "http://localhost:8080/customers"

const customerUrl = (customerId: number) => `${hostWithCustomers}/${customerId}`

const carUrl = (customerId: number, carUrl: string) => `${customerUrl(customerId)}/cars/${carUrl}`

const createCustomer = async (): Promise<any> => {
    const response = await axios.post("http://localhost:8080/customers", {
        firstName: "John",
        lastName: "Smith",
        city: "Los Angeles",
        country: "USA",
        email: `${randomUUID()}@gmailer.com`,
    })

    expect(response.status).toBe(201)
    return response.data
}

const createCar = async (): Promise<any> => {
    const customer = await createCustomer()
    const headers = getAuthHeadersForCustomer(customer)
    const response = await axios.post(
        `${customerUrl(customer.id)}/cars`,
        {
            model: "Jetta",
            make: "VW",
            producedIn: "2018",
        },
        headers,
    )

    expect(response.status).toBe(201)
    return {
        customer,
        car: response.data,
    }
}

const verifyCustomerAuth = (func: (url: string, config: any) => Promise<any>) => {
    it("should return 403 when accessing another customer", async () => {
        const customer = await createCustomer()
        const headers = getAuthHeadersForCustomer(customer)
        const response = await func(customerUrl(10000000), { ...headers, validateStatus: (status: any) => true })

        expect(response.status).toBe(403)
    })

    it("should return 401 when no auth is provided", async () => {
        const response = await func(customerUrl(10000000), { validateStatus: (status: any) => true })

        expect(response.status).toBe(401)
    })
}

const checkFailingRequestsForCarRoutes = (func: (url: string, config: any) => Promise<any>) => {
    it("should return 403 when accessing another customer", async () => {
        const { customer: customerOne, car: carOne } = await createCar()
        const customerTwo = await createCustomer()
        const headers = getAuthHeadersForCustomer(customerTwo)
        const response = await func(carUrl(customerOne.id, carOne.id), {
            ...headers,
            validateStatus: (status: any) => true,
        })

        expect(response.status).toBe(403)
    })

    it("should return 401 when no auth is provided", async () => {
        const response = await func(carUrl(10000000, randomUUID()), { validateStatus: (status: any) => true })

        expect(response.status).toBe(401)
    })

    it("should return 404 when auth is provided but car is not found", async () => {
        const customer = await createCustomer()
        const headers = getAuthHeadersForCustomer(customer)
        const response = await func(carUrl(customer.id, randomUUID()), {
            ...headers,
            validateStatus: (status: any) => true,
        })

        expect(response.status).toBe(404)
    })
}

describe("When performing requests against the api", () => {
    describe("GET /customers/{customerId}", () => {
        it("should succeed with correct inputs", async () => {
            const customer = await createCustomer()
            const headers = getAuthHeadersForCustomer(customer)
            const response = await axios.get(customerUrl(customer.id), headers)

            expect(response.status).toBe(200)
            expect(response.data).toStrictEqual({
                apiKey: customer.apiKey,
                id: customer.id,
                firstName: "John",
                lastName: "Smith",
                city: "Los Angeles",
                country: "USA",
                email: customer.email,
            })
        })

        verifyCustomerAuth(async (url, config) => {
            return axios.get(url, config)
        })
    })

    describe("POST /customers should succeed", () => {
        it("should succeed with correct inputs", async () => {
            const customer = await createCustomer()

            expect(customer).toStrictEqual({
                apiKey: customer.apiKey,
                id: customer.id,
                firstName: "John",
                lastName: "Smith",
                city: "Los Angeles",
                country: "USA",
                email: customer.email,
            })
        })
    })

    describe("DELETE /customers/{customerId} should succeed", () => {
        it("should succeed with correct inputs", async () => {
            const customer = await createCustomer()
            const headers = getAuthHeadersForCustomer(customer)
            const response = await axios.delete(customerUrl(customer.id), headers)

            expect(response.status).toBe(204)
        })

        verifyCustomerAuth(async (url, config) => {
            return axios.delete(url, config)
        })
    })

    describe("PATCH /customers/{customerId} should succeed", () => {
        it("should succeed with correct inputs", async () => {
            const customer = await createCustomer()
            const headers = getAuthHeadersForCustomer(customer)

            const response = await axios.patch(
                customerUrl(customer.id),
                {
                    id: customer.id,
                    firstName: "Jim",
                    lastName: customer.lastName,
                    city: customer.city,
                    country: customer.country,
                    email: customer.email,
                },
                headers,
            )

            expect(response.status).toBe(200)
            expect(response.data.firstName).toBe("Jim")
        })

        verifyCustomerAuth(async (url, config) => {
            return axios.patch(url, {}, config)
        })
    })

    describe("GET /customers/{customerId}/cars/{carId} should succeed", () => {
        it("should succeed with correct inputs", async () => {
            const { customer, car } = await createCar()
            const headers = getAuthHeadersForCustomer(customer)
            const response = await axios.get(carUrl(customer.id, car.id), headers)

            expect(response.status).toBe(200)
            expect(response.data).toStrictEqual({
                id: car.id,
                customerId: customer.id,
                model: "Jetta",
                make: "VW",
                producedIn: 2018,
            })
        })

        checkFailingRequestsForCarRoutes(async (url, headers) => {
            return axios.get(url, headers)
        })
    })

    describe("POST /customers/{customerId}/cars should succeed", () => {
        it("should succeed with correct inputs", async () => {
            await createCar()
        })

        verifyCustomerAuth(async (url, config) => {
            return axios.post(`${url}/cars`, {}, config)
        })
    })

    describe("DELETE /customers/{customerId}/cars/{carId} should succeed", () => {
        it("should succeed with correct inputs", async () => {
            const { customer, car } = await createCar()
            const headers = getAuthHeadersForCustomer(customer)
            const response = await axios.delete(carUrl(customer.id, car.id), headers)

            expect(response.status).toBe(204)
        })

        checkFailingRequestsForCarRoutes(async (url, headers) => {
            return axios.delete(url, headers)
        })
    })

    describe("PATCH /customers/{customerId}/cars/{carId} should succeed", () => {
        it("should succeed with correct inputs", async () => {
            const { customer, car } = await createCar()
            const headers = getAuthHeadersForCustomer(customer)
            const response = await axios.patch(
                carUrl(customer.id, car.id),
                {
                    model: "Jetta",
                    make: "VW",
                    producedIn: 2021,
                },
                headers,
            )

            expect(response.status).toBe(200)
            expect(response.data.producedIn).toBe(2021)
        })

        checkFailingRequestsForCarRoutes(async (url, headers) => {
            return axios.patch(
                url,
                {
                    model: "Jetta",
                    make: "VW",
                    producedIn: 2023,
                },
                headers,
            )
        })
    })
})
