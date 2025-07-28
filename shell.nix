{ pkgs ? import <nixpkgs> {} }:

pkgs.mkShell {
  buildInputs = with pkgs; [
    maven
    temurin-bin
  ];

  shellHook = ''
    alias build="mvn compile"
    alias run="mvn compile exec:java"
  '';
}
