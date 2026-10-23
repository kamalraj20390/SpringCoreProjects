package com.adda.app.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global Exception Handler for the Vehicle Service Management Application.
 *
 * <p>Annotated with {@link ControllerAdvice}, this class intercepts exceptions thrown by
 * controller handler methods across the entire application. It acts as a centralized
 * interceptor to decouple error handling logic from business and web controllers.</p>
 *
 * <p>Key Responsibilities:
 * <ul>
 *   <li>Intercept domain and runtime exceptions uniformly.</li>
 *   <li>Map technical errors into user-friendly error messages and titles.</li>
 *   <li>Feed model attributes into the unified error view ({@code templates/error.html}).</li>
 * </ul>
 * </p>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles cases where a requested entity cannot be found in the database
     * (e.g., looking up a non-existent customer, vehicle, job card, or invoice by ID).
     *
     * @param ex    The {@link ResourceNotFoundException} instance containing the specific missing resource details.
     * @param model Spring MVC {@link Model} used to pass data to the Thymeleaf template.
     * @return The view name of the general error page ("error").
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleNotFound(ResourceNotFoundException ex, Model model) {
        model.addAttribute("errorTitle", "Not Found");
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    /**
     * Handles violations of application business rules and domain invariants
     * (e.g., attempting to convert an already converted/cancelled booking,
     * invalid job card status transitions, insufficient spare parts stock, or
     * recording payment amounts that exceed the outstanding balance).
     *
     * @param ex    The {@link BusinessRuleException} carrying the validation failure explanation.
     * @param model Spring MVC {@link Model} used to expose error details to the view.
     * @return The view name of the general error page ("error").
     */
    @ExceptionHandler(BusinessRuleException.class)
    public String handleBusinessRule(BusinessRuleException ex, Model model) {
        model.addAttribute("errorTitle", "Business Rule Violation");
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    /**
     * Fallback exception handler for any unexpected, unhandled exceptions
     * (e.g., NullPointerExceptions, database connection timeouts, system-level failures).
     *
     * <p>Catches {@link Exception} to ensure users always receive a graceful, branded
     * error page instead of a raw stack trace or default browser 500 white-label error.</p>
     *
     * @param ex    The unexpected {@link Exception}.
     * @param model Spring MVC {@link Model} for error display.
     * @return The view name of the general error page ("error").
     */
    @ExceptionHandler(Exception.class)
    public String handleGeneral(Exception ex, Model model) {
        model.addAttribute("errorTitle", "Unexpected Error");
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}
