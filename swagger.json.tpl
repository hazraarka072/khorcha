{
  "openapi": "3.0.0",
  "info": {
    "title": "User Registration API",
    "version": "1.0",
    "description": "API for registering new users"
  },
  "paths": {
    "/register": {
      "post": {
        "summary": "Register a new user",
        "description": "This endpoint registers a new user and returns a success message.",
        "operationId": "registerUser",
        "requestBody": {
          "required": true,
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/RegistrationUser"
              }
            }
          }
        },
        "responses": {
          "200": {
            "description": "Registration successful",
            "content": {
              "application/json": {
                "schema": {
                  "$ref": "#/components/schemas/SuccessResponse"
                }
              }
            }
          },
          "400": {
            "description": "Invalid user data",
            "content": {
              "application/json": {
                "schema": {
                  "$ref": "#/components/schemas/ErrorResponse"
                }
              }
            }
          }
        },
        "x-amazon-apigateway-integration": {
          "uri": "arn:aws:apigateway:us-east-1:lambda:path/2015-03-31/functions/${lambda_function_arn}/invocations",
          "httpMethod": "POST",
          "type": "aws_proxy",
          "passthroughBehavior": "WHEN_NO_MATCH"
        }
      }
    }
  },
  "components": {
    "schemas": {
      "RegistrationUser": {
        "type": "object",
        "properties": {
          "username": {
            "type": "string"
          },
          "password": {
            "type": "string"
          }
        },
        "required": ["username", "password"]
      },
      "SuccessResponse": {
        "type": "object",
        "properties": {
          "message": {
            "type": "string",
            "example": "User registered successfully"
          }
        }
      },
      "ErrorResponse": {
        "type": "object",
        "properties": {
          "error": {
            "type": "string",
            "example": "Invalid user data"
          }
        }
      }
    }
  }
}
