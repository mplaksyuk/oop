import React from "react";
import { useAuth0 } from "@auth0/auth0-react";
import Button from 'react-bootstrap/Button';

export function Auth0Button() {
  const { loginWithRedirect } = useAuth0();

  return <Button type="submit" variant="success" onClick={() => loginWithRedirect()}>Auth0</Button>;
};
