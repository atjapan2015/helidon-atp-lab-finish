/*
 * Copyright (c) 2018, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.helidon.atp.lab;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.sql.DataSource;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Template;

import io.helidon.atp.lab.common.config.GlobalConfig;
import io.helidon.atp.lab.entity.Dept;
import io.helidon.atp.lab.entity.Emp;
import io.helidon.atp.lab.entity.Employee;
import io.helidon.atp.lab.facade.GreetFacade;

/**
 * A simple JAX-RS resource to greet you. Examples:
 *
 * Get default greeting message: curl -X GET http://localhost:8080/greet
 *
 * Get greeting message for Joe: curl -X GET http://localhost:8080/greet/Joe
 *
 * Change greeting curl -X PUT -H "Content-Type: application/json" -d
 * '{"greeting" : "Howdy"}' http://localhost:8080/greet/greeting
 *
 * The message is returned as a JSON object.
 */
@Path("/greet")
@RequestScoped
public class GreetResource {

	private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

	/**
	 * The greeting message provider.
	 */
	private final GreetingProvider greetingProvider;

	@Inject
	GreetFacade greetFacade;

	/**
	 * Using constructor injection to get a configuration property. By default this
	 * gets the value from META-INF/microprofile-config
	 *
	 * @param greetingConfig the configured greeting message
	 */
	@Inject
	public GreetResource(@Named("helidon") DataSource ds, GreetingProvider greetingConfig) {
		GlobalConfig.ds = ds;
		this.greetingProvider = greetingConfig;
	}

	/**
	 * Return a wordly greeting message.
	 *
	 * @return {@link JsonObject}
	 */
	@SuppressWarnings("checkstyle:designforextension")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JsonObject getDefaultMessage() {
		return createResponse("World");
	}

	/**
	 * Return a greeting message using the name that was provided.
	 *
	 * @param name the name to greet
	 * @return {@link JsonObject}
	 */
	@SuppressWarnings("checkstyle:designforextension")
	@Path("/{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JsonObject getMessage(@PathParam("name") String name) {
		return createResponse(name);
	}

	/**
	 * Set the greeting to use in future messages.
	 *
	 * @param jsonObject JSON containing the new greeting
	 * @return {@link Response}
	 */
	@SuppressWarnings("checkstyle:designforextension")
	@Path("/greeting")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateGreeting(JsonObject jsonObject) {

		if (!jsonObject.containsKey("greeting")) {
			JsonObject entity = JSON.createObjectBuilder().add("error", "No greeting provided").build();
			return Response.status(Response.Status.BAD_REQUEST).entity(entity).build();
		}

		String newGreeting = jsonObject.getString("greeting");

		greetingProvider.setMessage(newGreeting);
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@Path("/employees")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Employee> listEmployees() throws SQLException {

		List<Employee> employees = greetFacade.listEmployees();
		return employees;
	}

	@Path("/employees")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Employee insertEmployee(Employee employee) throws SQLException {

		Employee newEmployee = greetFacade.insertEmployee(employee);
		return newEmployee;
	}

	@Path("/employees")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Employee updateEmployee(Employee employee) throws SQLException {

		Employee newEmployee = greetFacade.updateEmployee(employee);
		return newEmployee;
	}

	@Path("/employees/{employeeId}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteEmployee(@PathParam("employeeId") String employeeId) throws SQLException {

		String message = "Failed!";
		int deleteCount = greetFacade.deleteEmployee(employeeId);
		if (deleteCount == 1) {
			message = "Succeed!";
		}
		return message;
	}

	@Path("/emp")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Emp> listEmp() throws SQLException {

		List<Emp> emps = greetFacade.listEmp();
		return emps;
	}

	@Path("/emp")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Valid
	public Emp insertEmp(Emp emp) throws SQLException {

		Emp newEmp = greetFacade.insertEmp(emp);
		return newEmp;
	}

	@Path("/dept")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Dept> listDept() throws SQLException {

		List<Dept> depts = greetFacade.listDept();
		return depts;
	}

	@Path("/dept")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Dept insertDept(Dept dept) throws SQLException {

		Dept newDept = greetFacade.insertDept(dept);
		return newDept;
	}

	@GET
	// @ErrorTemplate(name = "/error.mustache")
	@Template(name = "/employees.mustache")
	@Path("/web/mustache/employees")
	@Produces(MediaType.TEXT_HTML)
	public Map<String, Object> showEmployeesInMustacha() {

		List<Employee> employees = greetFacade.listEmployees();

		Map<String, Object> model = new HashMap<>();
		model.put("employees", employees);
		return model;
	}

	@GET
	// @ErrorTemplate(name = "/error.mustache")
	@Template(name = "/employees.ftl")
	@Path("/web/freemarker/employees")
	@Produces(MediaType.TEXT_HTML)
	public Map<String, Object> showEmployeesInFreemarker() {

		List<Employee> employees = greetFacade.listEmployees();

		Map<String, Object> model = new HashMap<>();
		model.put("employees", employees);
		return model;
	}

	private JsonObject createResponse(String who) {

		String msg = String.format("%s %s!", greetingProvider.getMessage(), who);
		return JSON.createObjectBuilder().add("message", msg).build();
	}
}
