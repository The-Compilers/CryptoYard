import { useEffect } from "react";

/**
 * Hook that calls a callback function when the component passed
 * as ref is clicked outside
 * @param {*} ref the component to watch if clicked outside
 * @param {*} callback the callback to call when a clicked outside
 */
export const useOutsideHandler = (ref, callback) => {
  useEffect(() => {
    /**
     * Call callback function if clicked outside of component
     * @param {*} event the event triggered when clicking
     */
    const handleClickOutside = (event) => {
      if (ref.current && !ref.current.contains(event.target)) {
        callback();
      }
    };

    // Bind the event listener
    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      // Unbind the event listener on clean up
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [ref]);
};
